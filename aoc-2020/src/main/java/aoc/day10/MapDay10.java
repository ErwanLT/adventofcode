package aoc.day10;

import java.util.HashMap;
import java.util.stream.Collectors;

public class MapDay10<K> extends HashMap<K, Long> {

    public MapDay10() {
    }

    public Long increment(K key) {
        return super.put(key, super.containsKey(key) ? super.get(key) + 1 : 1);
    }

    public Long increment(K key, long amount) {
        return super.put(key, super.containsKey(key) ? super.get(key) + amount : amount);
    }

    @Override
    public Long get(Object key){
        if(!super.containsKey(key))
            super.put((K) key, 0L);
        return super.get(key);
    }

    @Override
    public String toString() {
        return keySet().stream().sorted().map(e -> e+"\t"+get(e)).collect(Collectors.joining(System.lineSeparator()));
    }
}
