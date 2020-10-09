package com.xknower.utils.okhttp.client;

import com.xknower.utils.okhttp.exception.HttpException;
import com.xknower.utils.okhttp.result.JsonResultExecute;
import com.xknower.utils.okhttp.result.ResultExecute;
import com.xknower.utils.okhttp.result.StopWatch;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTP 客户端 [初始化Bean 注入 OkHttpClient]
 *
 * @author xknower
 */
public class HttpClient {

    private OkHttpClient client;

    private HttpClient() {
    }

    public HttpClient(OkHttpClient client) {
        this.client = client;
    }

    public <T> T execute(HttpParams params, ResultExecute<T> resultExecute) throws HttpException {
        Request request = params.toRequest();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            stopWatch.stop();
            return resultExecute.toBody(response, request, stopWatch);
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw HttpException.make(e, request, stopWatch);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 指定请求
     *
     * @param params        请求参数
     * @param resultExecute JsonResultExecute
     * @param clz           序列化JSON
     * @param <T>
     * @return
     * @throws HttpException
     */
    public <T> T executeToJsonResultExecute(HttpParams params, JsonResultExecute<T> resultExecute, Class<? extends T> clz) throws HttpException {
        Request request = params.toRequest();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            stopWatch.stop();
            return resultExecute.toBody(response, request, stopWatch, clz);
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw HttpException.make(e, request, stopWatch);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 获取请求对象
     *
     * @return OkHttpClient
     */
    public OkHttpClient okHttpClient() {
        return client;
    }
}
