package io.cc.mq.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author nhsoft.lsd
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private int code;
    private T result;

    public static Result<String> ok() {
        return new Result<>(0, "ok");
    }

    public static Result<CCMessage<?>> msg(final CCMessage<?> message) {
        return new Result<>(0, message);
    }

    public static <T> Result<T> data(T data) {
        return new Result<>(0, data);
    }
}
