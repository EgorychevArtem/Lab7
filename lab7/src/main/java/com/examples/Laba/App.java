package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class App {
    public static void main(String[] args){
        ZContext zContext = new ZContext();
        ZMQ.Socket client = zContext.createSocket(SocketType.REQ);
        client.setHWM(0);
        client.connect()
    }
}
