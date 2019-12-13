package com.examples.Laba;

import org.zeromq.ZFrame;

public class CacheDealerMeta {
    ZFrame id;
    long lastBeat;
    int start, end;

    public CacheDealerMeta(ZFrame id){
        this.id = id;
        this.lastBeat = System.currentTimeMillis();
    }
}
