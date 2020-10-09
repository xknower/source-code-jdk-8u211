package com.xknower.chromium.net.entity;

import com.xknower.utils.BufferEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取请求的数据
 * <p>
 * // HTTP请求生命周期
 * // BeforeRequest (BeforeURLRequestParams) -> BeforeRedirect (BeforeRedirectParams) -> BeforeSendHeader (BeforeSendHeadersParams) -> SendHeader (SendHeadersParams)
 * // HeadersReceived (HeadersReceivedParams) ->  ResponseStarted (ResponseStartedParams) -> DataReceived (DataReceivedParams)
 * // Completed (RequestCompletedParams) -> Destroyed (RequestParams)
 */
public class HttpLifeCycleData {

    private HttpDataType dataType;

    private long requestId;

    private String method;

    private String url;

    List<HttpData> httpDataList = new ArrayList<>();

    public void setHttpData(HttpData httpData) {
        if (requestId == 0) {
            requestId = httpData.getRequestId();
            method = httpData.getMethod();
            url = httpData.getUrl();
        }

        //
        dataType = httpData.getDataType();

        httpDataList.add(httpData);
    }

    /**
     * 获取接收的数据
     */
    public byte[] getAllData() {
        //
        BufferEntity bufferEntity = new BufferEntity();
        //
        List<byte[]> data = httpDataList.stream()
                .sorted(Comparator.comparing(HttpData::getId))
                .filter(d -> d.getDataType() == HttpDataType.DataReceived)
                .filter(d -> d.getData() != null && d.getData().length > 0)
                .map(HttpData::getData).collect(Collectors.toList());
        data.forEach(d -> bufferEntity.put(d));
        return bufferEntity.getAll();
    }

    public HttpData getHttpData() {
        return httpDataList.stream()
                .sorted(Comparator.comparing(HttpData::getId)).findFirst().get();
    }

    public List<HttpData> getHttpDataReceived() {
        return httpDataList.stream()
                .sorted(Comparator.comparing(HttpData::getId))
                .filter(d -> d.getDataType() == HttpDataType.DataReceived)
                .collect(Collectors.toList());
    }
}
