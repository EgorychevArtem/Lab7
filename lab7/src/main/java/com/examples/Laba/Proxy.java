package com.examples.Laba;

import org.zeromq.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class Proxy implements Closeable {
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
            context.bind();
            context.handle();
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
        clientRouter.bind("tcp://localhost:3000");
        cacheRouter.bind("tcp://localhost:3001");
        poller.register(clientRouter, ZMQ.Poller.POLLIN);
        poller.register(cacheRouter, ZMQ.Poller.POLLIN);
    }

    public void handle(){
        while (!Thread.currentThread().isInterrupted()){
            poller.poll();
            if(poller.pollin(0)){
                boolean ok = true;
                ZMsg msg = ZMsg.recvMsg(clientRouter);
                log.info("Received message from ClientRouter: " + msg);
                ZFrame clienId = msg.pop();
                msg.pop();
                Command cmd = Command.fromStr(msg.popString());
                if(cmd.matchType(CommandType.GET)){
                    Optional<ZFrame> dealerId = dealers.getDealerId(cmd.getIndex());
                    if(dealerId.isPresent()){
                        sendCommandtoDealer(dealerId.get(), clienId, cmd);
                    }
                }
            }
            if(poller.pollin(1)){

            }
        }
    }

                @Override
    public void close() throws IOException {
        poller.close();
        context.destroySocket(cacheRouter);
        context.destroySocket(clientRouter);
    }
}
