package com.examples.Laba;

import org.zeromq.ZFrame;

public class CacheDealerMeta {
    ZFrame id;
    long lastBeat;
    int start, end;

    public CacheDealerMeta(ZFrame id){
        this.id = id;
        this.lastBeat = System.currentTimeMillis();
        this.start =0;
        this.end = 0;
    }

    public boolean inside(int val){
        return start <= val && val<= end;
    }


    public boolean isAlive() {
    }
}
