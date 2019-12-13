package com.examples.Laba;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.Closeable;
import java.util.logging.Logger;

public class Client implements Closeable{
    private static final Logger log = Logger.getLogger(Client.class.getName());
    ZContext context;
    ZMQ.Socket Socket;
    public static void main(String[] args) throws IOException {
        try (
                Scanner scanner = new Scanner(System.in);
                ZContext context = new ZContext();
                Client client = new Client(context)
        ) {
            client.connect(Proxy.getClientAddr());
            client.handle(scanner, System.out);
        }
    }
    public Client(ZContext context) {
        this.context = context;
        this.Socket = context.createSocket(SocketType.REQ);
    }

    private void connect(String clientAddr) {
        Socket.connect(clientAddr);
    }

    private void handle(Scanner scanner, PrintStream out) {
        while (!Thread.currentThread().isInterrupted()){
            out.print(" ");
            Command cmd = Command.fromStr(scanner.nextLine());
            if(cmd.matchType(CommandType.GET)){
                int res = SendMsg(cmd);
            }
        }
    }

    private int SendMsg(Command cmd) {
        ZMsg msg = ZMsg.newStringMsg(cmd.toString());
    }

    @Override
    public void close() throws IOException {
        context.destroySocket(Socket);
    }
}
