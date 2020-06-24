package com.xknower.utils.okhttp.result;

import com.alibaba.fastjson.JSON;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * @param <T>
 * @author xknower
 */
public class JsonArrayResultExecute<T> {

    private final StringResultExecute stringResultExecute = new StringResultExecute();

    private Class<T> clazz;

    public <T> List<T> toBody(Response response, Request request, StopWatch stopWatch) throws IOException {
        String body = stringResultExecute.toBody(response, request, stopWatch);
        return JSON.parseArray(body, (Class<T>) clazz);
    }
}
