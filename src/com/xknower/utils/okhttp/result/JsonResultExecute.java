package com.xknower.utils.okhttp.result;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 基于T JSON 序列化
 *
 * @param <T>
 * @author xknower
 */
public interface JsonResultExecute<T> {

    /**
     * 解析HTTP消息体
     *
     * @param response
     * @param request
     * @param stopWatch
     * @param clz
     * @return
     * @throws Exception
     */
    T toBody(Response response, Request request, StopWatch stopWatch, Class<? extends T> clz) throws Exception;
}
