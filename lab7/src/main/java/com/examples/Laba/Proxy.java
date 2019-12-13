package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.Closeable;
import java.io.IOException;

public class Proxy implements Closeable {
    public ZContext context;
    public ZMQ.Socket clientRouter, cacheRouter;
    public ZMQ.Poller poller;
    public CacheDealerStorage dealers;
    public static void main(String[] args) throws IOException {
        try (
                ZContext context = new ZContext();
                Proxy proxy = new Proxy(context)
        ) {

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



                @Override
    public void close() throws IOException {

    }
}
