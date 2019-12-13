package com.examples.Laba;

import java.util.Arrays;

public class CacheStorage {
    int start, end;
    int[] cache = new int[end - start +1];

    public CacheStorage(int start, int end, int init){
        this.start = start;
        this.end = end;
        Arrays.fill(cache, init);
    }

    public int getCache(int i){
        return cache[i -start];
    }

    public void setCache(int i, int val){
        cache[i - start] = val;
    }
}
