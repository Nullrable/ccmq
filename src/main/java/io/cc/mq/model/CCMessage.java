package io.cc.mq.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * @author nhsoft.lsd
 */
@Data
public class CCMessage<T> {

    private Long id;

    private T body;

    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> properties = new HashMap<>();

    public CCMessage(final Long id, final T body) {
        this.id = id;
        this.body = body;
    }
}
