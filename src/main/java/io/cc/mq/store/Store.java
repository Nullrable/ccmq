package io.cc.mq.store;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;

/**
 * @author nhsoft.lsd
 */
public class Store {

    private final String topic;

    private MappedByteBuffer mapBuffer;

    private final Indexer indexer;

    public static final int LEN = 1024 * 10;//

    public Store(final String topic) {
        this.topic = topic;
        this.indexer = new Indexer();
        init();
    }

    @SneakyThrows
    private  void init() {

        try (FileChannel channel = new RandomAccessFile(topic + ".dat", "rw").getChannel()) {
            //一个整数4个字节
            mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, LEN);

            byte[] header = new byte[10];
            mapBuffer.get(header);
            int pos = 0;
            int len;
            try {
                len = Integer.parseInt(new String(header, StandardCharsets.UTF_8));
            } catch (Exception e) {
                return;
            }
            while (len > 0) {
                indexer.addEntry(pos, len);
                pos = pos + len ;
                System.out.println(" next pos =====> " + pos);
                mapBuffer.position(pos);
                mapBuffer.get(header);
                String lens = new String(header, StandardCharsets.UTF_8);
                System.out.println(" lens =====> " + lens);
                try {
                    len = Integer.parseInt(lens);
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    public int add(String content) {

//        String msg = JSON.toJSONString(message);

        int len = content.getBytes(StandardCharsets.UTF_8).length;

        int pos = mapBuffer.position();

        len = len + 10;

        indexer.addEntry(pos, len);

        String format = String.format("%010d", len);

        content = format + content;

        mapBuffer.put(content.getBytes(StandardCharsets.UTF_8));

        return pos;
    }

    public String get(int pos) {
        int len = indexer.getEntry(pos).getLen();
        if (len == 0) {
            return null;
        }
        byte[] bytes = new byte[len - 10];
        mapBuffer.position(pos + 10);
        mapBuffer.get(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public int next_pos(int pos) {
        if (pos == -1) {
            return 0;
        }
        Entry entry = indexer.getEntry(pos);
        if (entry == null) {
            return -1;
        }
        return pos + entry.getLen();
    }
}

