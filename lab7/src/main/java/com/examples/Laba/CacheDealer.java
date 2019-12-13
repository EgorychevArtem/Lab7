package com.examples.Laba;

import org.zeromq.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

public class CacheDealer implements Closeable {
    private static final Logger log = Logger.getLogger(CacheDealer.class.getName());
    public static long BeatDurarion = 500;
    public ZContext context;
    public ZMQ.Socket Socket;
    public ZMQ.Poller poller;
    public CacheStorage storage;
    long next;

    public static void main(String[] args) throws IOException {
        int start, end, init;
        start = ParseInt(args[0]);
        end = ParseInt(args[1]);
        init = ParseInt(args[2]);

        CacheStorage storage = new CacheStorage(start,end,init);
        try (
                ZContext context = new ZContext();
                CacheDealer dealer = new CacheDealer(context,storage);
                ){
            dealer.connect(Proxy.getCacheDealerAddr());
            dealer.handle();
        }
    }

    public void handle() {
        while (!Thread.currentThread().isInterrupted()){
            long currentTime = System.currentTimeMillis();
            poller.poll(Math.max(0, next - currentTime));
            if(poller.pollin(0)){ //DealerPollin
                ZMsg msg = ZMsg.recvMsg(Socket);
                log.info("Received msg: " + msg);

                ZFrame clientId = msg.pop();
                Command cmd = Command.fromStr(msg.popString());
                if(cmd.matchType(CommandType.GET)){
                    
                }
            }
        }
    }

    private static int ParseInt(String arg) {
        return Integer.parseInt(arg);
    }

    public CacheDealer(ZContext context, CacheStorage storage){
        this.context = context;
        this.storage = storage;
        this.Socket = context.createSocket(SocketType.DEALER);
        this.poller = context.createPoller(1);
    }

    public void connect(String addr){
        Socket.connect(addr);
        poller.register(Socket, ZMQ.Poller.POLLIN);
    }

    @Override
    public void close() throws IOException {
        poller.close();
        context.destroySocket(Socket);
    }

    public static long getBeatDuration(){
        return BeatDurarion;
    }
}
