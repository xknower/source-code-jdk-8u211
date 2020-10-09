package com.xknower.utils.okhttp;

import com.xknower.utils.okhttp.result.StopWatch;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 请求响应数据
 *
 * @author xknower
 */
public class OkResponse {

    private Response response;

    private Request request;

    private StopWatch stopWatch;

    /**
     * 响应消息体, 文本数据
     */
    private String body;

    /**
     * 响应错误数据
     */
    private String error;

    public boolean isSuccess() {
        return error != null && !"".equals(error);
    }

    public OkResponse(Response response, Request request, StopWatch stopWatch) {
        this.response = response;
        this.request = request;
        this.stopWatch = stopWatch;
    }

    /**
     * 请求成功后, 延迟分析消息体
     *
     * @return OkResponse
     */
    public OkResponse analysisBody() throws IOException {
        body = response.body().string();
        return this;
    }

    //

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public StopWatch getStopWatch() {
        return stopWatch;
    }

    public void setStopWatch(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
