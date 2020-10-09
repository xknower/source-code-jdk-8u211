package com.xknower.utils.okhttp;

import com.xknower.utils.okhttp.client.HttpClient;
import com.xknower.utils.okhttp.result.StopWatch;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class XkDfsService extends OkHttpExecuteHandler {

    public XkDfsService(HttpClient httpClient) {
        super(httpClient);
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
