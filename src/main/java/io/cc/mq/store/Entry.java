package io.cc.mq.store;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author nhsoft.lsd
 */
@Data
@AllArgsConstructor
public class Entry {

    private int pos;
    private int len;
}
