package com.examples.Laba;

import org.zeromq.ZContext;

import java.io.Closeable;
import java.io.IOException;

public class Proxy implements Closeable {
    public static void main(String[] args) {
        try (
                ZContext context = new ZContext();
                Proxy proxy = new Proxy(context)
        ){

        }

        



                @Override
    public void close() throws IOException {

    }
}
