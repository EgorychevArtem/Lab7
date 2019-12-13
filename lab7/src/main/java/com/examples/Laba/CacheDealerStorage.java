package com.examples.Laba;

import org.zeromq.ZFrame;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CacheDealerStorage {
    Map<String, CacheDealerMeta> dealers = new TreeMap<>();

    public Optional<ZFrame> getDealerId(int id){
        
    }
}
