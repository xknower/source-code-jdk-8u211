package com.xknower.utils.okhttp.result;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求响应结果, 回调执行
 *
 * @param <T>
 * @author xknower
 */
public interface ResultExecute<T> {
    /**
     * 解析HTTP消息体
     *
     * @param response  响应数据
     * @param request   请求数据
     * @param stopWatch 监控体
     * @return
     * @throws Exception 抛出异常
     */
    T toBody(Response response, Request request, StopWatch stopWatch) throws Exception;
}
