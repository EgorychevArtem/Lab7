package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.io.PrintStream;
import java.util.Scanner;

public class Client {
    ZContext context;
    ZMQ.Socket Socket;
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                ZContext context = new ZContext();
                Client client = new Client(context)
        ) {
            client.connect(Proxy.getClientAddr());
            client.handle(scanner, System.out);
        }
    }

    private void handle(Scanner scanner, PrintStream out) {
    }


    public Client(ZContext context) {
        this.context = context;
        this.Socket = context.createSocket(SocketType.REQ);
    }

    }
}
