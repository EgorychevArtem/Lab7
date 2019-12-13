package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try (
            ZContext context = new ZContext();
            Scanner scanner = new Scanner(System.in);
            Client client = new Client(context)
        ) {
            client.connect(Proxy.getClientRouterAddr());
        }


         public Client(ZContext context) {
                this.context = context;
        }

    }
}
