package io.cc.mq.model;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author nhsoft.lsd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CCMessage {

    private Long id;

    private String body;

    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> properties = new HashMap<>();

    public CCMessage(final Long id, final String body) {
        this.id = id;
        this.body = body;
    }
}
