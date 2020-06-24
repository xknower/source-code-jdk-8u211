package com.xknower.utils.okhttp.result;

import com.alibaba.fastjson.JSON;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @param <T>
 * @author xknower
 */
public class JsonObjectResultExecute<T> {

    private final StringResultExecute stringResultExecute = new StringResultExecute();

    private Class<T> clazz;

    public <T> T toBody(Response response, Request request, StopWatch stopWatch) throws IOException {
        String body = stringResultExecute.toBody(response, request, stopWatch);
        return JSON.parseObject(body, (Class<T>) clazz);
    }
}
