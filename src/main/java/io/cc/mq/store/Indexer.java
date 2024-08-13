package io.cc.mq.store;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nhsoft.lsd
 */
public class Indexer {

    private Map<Integer, Entry> mapping = new HashMap<>();

    public void addEntry(int pos, int len) {
         mapping.put(pos, new Entry(pos, len));
    }

    public Entry getEntry(int pos) {
        Entry entry = mapping.get(pos);
        return entry;
    }
}
