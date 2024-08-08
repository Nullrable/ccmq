package io.cc.mq.util;

import com.alibaba.fastjson.JSON;
import io.cc.mq.model.Result;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author nhsoft.lsd
 */
public class HttpInvoker {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5_000, TimeUnit.MILLISECONDS)//设置连接超时时间
            .readTimeout(1_000, TimeUnit.MILLISECONDS)//设置读取超时时间
            .build();

    @SneakyThrows
    public static <T> T get(final String url, Class<T> tClass) {
        Request req = new Request.Builder().url(url).build();
        try (Response response = okHttpClient.newCall(req).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // 将响应的 JSON 字符串转换为 MyResponse 对象
            assert response.body() != null;
            String jsonResponse = response.body().string();

            return JSON.parseObject(jsonResponse).toJavaObject(tClass);
        }
    }

    @SneakyThrows
    public static <T> Result post(final String url, final T request) {
        RequestBody body = RequestBody.create(MEDIA_TYPE, JSON.toJSONString(request));
        Request req = new Request.Builder().url(url).post(body).build();
        ResponseBody responseBody = okHttpClient.newCall(req).execute().body();

        if (responseBody == null) {
            throw new RuntimeException("responseBody is null");
        }
        String resultJson = responseBody.string();
        Result result = JSON.parseObject(resultJson, Result.class);
        return result;
    }
}
