package com.xknower.utils.okhttp;

import com.xknower.utils.okhttp.client.HttpClient;
import com.xknower.utils.okhttp.client.HttpParams;
import com.xknower.utils.okhttp.exception.HttpException;
import com.xknower.utils.okhttp.result.ResultExecute;
import com.xknower.utils.okhttp.result.StopWatch;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author xknower
 */
public class OkHttpExecuteHandler implements ResultExecute<OkResponse> {

    private HttpClient httpClient;

    private OkHttpExecuteHandler() {
    }

    public OkHttpExecuteHandler(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * POST 执行HTTP请求
     * <p>
     *
     * @param request 请求体
     * @return OkResponse
     * @throws HttpException 请求抛出HTTP异常
     */
    public OkResponse callPostString(OkRequest request) {
        HttpParams params = HttpParams.postString(request.getUrl(), request.getBody());
        if (request.getHeads() != null && request.getHeads().size() > 0) {
            params.setHeads(request.getHeads());
        }
        return httpClient.execute(params, this);
    }

    /**
     * GET 执行HTTP请求
     *
     * @param request 请求体
     * @return OkResponse
     * @throws HttpException 请求抛出HTTP异常
     */
    public OkResponse callGet(OkRequest request) {
        HttpParams params = HttpParams.get(request.getUrl());
        if (request.getHeads() != null && request.getHeads().size() > 0) {
            params.setHeads(request.getHeads());
        }
        return httpClient.execute(params, this);
    }

    /**
     * 返回数据解析
     *
     * @param response  响应数据
     * @param request   请求数据
     * @param stopWatch 计时监控数据对象
     * @return
     * @throws Exception
     */
    @Override
    public OkResponse toBody(Response response, Request request, StopWatch stopWatch) throws Exception {
        OkResponse okResponse = new OkResponse(response, request, stopWatch);
        try {
            okResponse.analysisBody();
        } catch (IOException e) {
            //
            okResponse.setError("响应数据, 解析文本数据IO异常");
        } catch (Exception e) {
            okResponse.setError("响应数据, 解析异常");
        }
        return okResponse;
    }
}
