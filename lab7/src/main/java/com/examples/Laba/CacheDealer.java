package com.examples.Laba;

import org.zeromq.ZContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

public class CacheDealer implements Closeable {
    private static final Logger log = Logger.getLogger(CacheDealer.class.getName());
    public ZContext context;

    @Override
    public void close() throws IOException {

    }
}
