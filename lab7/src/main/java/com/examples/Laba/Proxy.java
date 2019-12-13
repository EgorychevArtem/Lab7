package com.examples.Laba;

import org.zeromq.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Proxy implements Closeable {
    private static String ClientAddr = "tcp://localhost:3000";
    private static String CacheDealerAddr = "tcp://localhost:3001";
    private static final Logger log = Logger.getLogger(Proxy.class.getName());
    public ZContext context;
    public ZMQ.Socket clientRouter, cacheRouter;
    public ZMQ.Poller poller;
    public CacheDealerStorage dealers;

    public static void main(String[] args) throws IOException {
        try (
                ZContext context = new ZContext();
                Proxy proxy = new Proxy(context)
        ) {
            proxy.bind();
            proxy.handle();
        }
    }

    public Proxy(ZContext context){
        this.context = context;
        this.clientRouter = context.createSocket(SocketType.ROUTER);
        this.cacheRouter = context.createSocket(SocketType.ROUTER);
        this.poller = context.createPoller(2);
        this.dealers = new CacheDealerStorage();
    }

    public void bind(){
        clientRouter.bind(ClientAddr);
        cacheRouter.bind(CacheDealerAddr);
        poller.register(clientRouter, ZMQ.Poller.POLLIN);
        poller.register(cacheRouter, ZMQ.Poller.POLLIN);
    }

    public void handle(){
        while (!Thread.currentThread().isInterrupted()){
            poller.poll();
            if(poller.pollin(0)){ //ClientPollin
                boolean ok = true;
                ZMsg msg = ZMsg.recvMsg(clientRouter);
                log.info("Received message from ClientRouter: " + msg);
                ZFrame clienId = msg.pop();
                msg.pop();
                Command cmd = Command.fromStr(msg.popString());
                if(cmd.matchType(CommandType.GET)) {
                    Optional<ZFrame> dealerId = dealers.getDealerId(cmd.getIndex());
                    if (dealerId.isPresent()) {
                        sendCommandtoDealer(dealerId.get(), clienId, cmd);
                    } else {
                        ok = false;
                    }
                } else if (cmd.matchType(CommandType.PUT)){
                    List<ZFrame> dealersId = dealers.getAllDealers(cmd.getIndex());
                    if(!dealersId.isEmpty()){
                        dealersId.forEach(id -> sendCommandtoDealer(id, clienId, cmd));
                        sendResultToClient(clienId, "OK");
                } else {
                        ok = false;
                    }
                }
                if(!ok){
                    sendResultToClient(clienId, CommandType.ERROR.toString());
                }
            }
        if(poller.pollin(1)){ //CachePollin
            ZMsg msg = new ZMsg();
            log.info("Received message from CacheRouter: " + msg);
            ZFrame dealerId = msg.pop();
            Command cmd = Command.fromStr(msg.popString());
            if(cmd.matchType(CommandType.RESULT)){
                ZFrame clientId = msg.pop();
                sendResultToClient(clientId, Command.OK(cmd.getResult()).toString());
            } else if(cmd.matchType(CommandType.NOTIFY)){
                dealers.insert(dealerId, cmd.getStart(), cmd.getEnd());
            }
        }
    }
}

    private void sendResultToClient(ZFrame clienId, String res) {
        ZMsg msg = new ZMsg();
        msg.add(clienId);
        msg.add("");
        msg.add(res);
        msg.send(clientRouter, false);
    }

    private void sendCommandtoDealer(ZFrame dealerId, ZFrame clienId, Command cmd) {
        ZMsg msg = new ZMsg();
        msg.add(dealerId);
        msg.add(clienId);
        msg.add(cmd.toString());
        log.info("Send command to dealer: " + msg);
        msg.send(cacheRouter, false);
    }

    @Override
    public void close() throws IOException {
        poller.close();
        context.destroySocket(cacheRouter);
        context.destroySocket(clientRouter);
    }

    
}
