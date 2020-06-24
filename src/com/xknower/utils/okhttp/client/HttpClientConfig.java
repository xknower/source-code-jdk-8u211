package com.xknower.utils.okhttp.client;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * 配置 HttpClient
 *
 * @author xknower
 */
public class HttpClientConfig {

    private int httpMaxConnect = 1;

    private long httpKeepAlice = 1;

    public static HttpClientConfig instance() {
        return new HttpClientConfig(10, 1);
    }

    public HttpClientConfig(int httpMaxConnect, long httpKeepAlice) {
        this.httpMaxConnect = httpMaxConnect;
        this.httpKeepAlice = httpKeepAlice;
    }

    public HttpClient httpClient() {
        return httpClient(null);
    }

    public HttpClient httpClient(OkHttpClient okHttpClient) {
        return new HttpClient(okHttpClient == null ? okHttpClient() : okHttpClient);
    }

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                //
                .connectTimeout(10, TimeUnit.SECONDS)
                //
                .followRedirects(false)
                //
                .readTimeout(1, TimeUnit.MINUTES)
                //
                .retryOnConnectionFailure(false)
                .writeTimeout(1, TimeUnit.MINUTES)
                //
                .connectionPool(new ConnectionPool(httpMaxConnect, httpKeepAlice, TimeUnit.MINUTES))
                .build();
    }
}
