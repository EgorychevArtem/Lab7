package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.Closeable;
import java.io.IOException;

public class Proxy implements Closeable {
    public ZContext context;
    public ZMQ.Socket clientRouter, cacheRouter, poller;
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
            
        }




                @Override
    public void close() throws IOException {

    }
}
