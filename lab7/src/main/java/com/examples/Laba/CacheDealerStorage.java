package com.examples.Laba;

import org.zeromq.ZFrame;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CacheDealerStorage {
    Map<String, CacheDealerMeta> dealers = new TreeMap<>();

    public Optional<ZFrame> getDealerId(int id) {
        return getAliveDealers(id)
                .findAny()
                .map(CacheDealerMeta::getId);
    }

    public List<ZFrame> getAllDealers(int id){
        return getAliveDealers(id)
                .map(CacheDealerMeta::getId)
                .collect(Collectors.toList());
    }

    public Stream<CacheDealerMeta> getAliveDealers(int id){
        dealers.values().stream()
                .filter(d -> d.inside(id))
                .filter(d -> !d.isAlive())
                .collect(Collectors.toList())
                .forEach(d -> dealers.remove(d.id.toString()));
        return dealers.values().stream()
                .filter(d -> d.inside(id));
    }

    public void insert(ZFrame id, int start, int end){
        CacheDealerMeta dealer = dealers.computeIfAbsent(id.toString(), k -> new CacheDealerMeta(id));
        dealer.updateBeat();
        dealer.setRange(start, end);
    }
}
