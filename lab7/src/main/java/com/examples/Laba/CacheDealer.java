package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

public class CacheDealer implements Closeable {
    private static final Logger log = Logger.getLogger(CacheDealer.class.getName());
    public ZContext context;
    public ZMQ.Socket Socket;
    public ZMQ.Poller poller;
    public CacheStorage storage;
    long next;

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
}
