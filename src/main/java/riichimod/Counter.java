package riichimod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class  Counter<T> implements Iterable<Integer> {
    private final HashMap<T, Integer> map = new HashMap<>();

    public void inc(T key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    public void dec(T key) {
        map.put(key, map.getOrDefault(key, 0) - 1);
    }

    public int get(T key) {
        return map.getOrDefault(key, 0);
    }

    public void reset(T key) {
        map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.values().iterator();
    }

    public Stream<Integer> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
