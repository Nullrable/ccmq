package io.cc.mq.demo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author nhsoft.lsd
 */
@AllArgsConstructor
@ToString
public class Order {

    private long id;

    private String orderNo;

    private BigDecimal price;
}
