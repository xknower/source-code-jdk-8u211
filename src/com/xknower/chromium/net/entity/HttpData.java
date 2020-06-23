package com.xknower.chromium.net.entity;

import com.teamdev.jxbrowser.chromium.RequestParams;

/**
 * HTTP 请求的数据
 *
 * @author xknower
 */
public class HttpData {

    private int id;

    // 类型

    private HttpDataType dataType;

    private long requestId;

    private String method;

    private String url;

    // 接收数据

    private String mimeType;

    private String charset;

    private byte[] data;

    public HttpData(int id, HttpDataType dataType, long requestId, String method, String url) {
        this.id = id;
        this.dataType = dataType;
        this.requestId = requestId;
        this.method = method;
        this.url = url;
    }

    public HttpData(int id, HttpDataType dataType, long requestId, String method, String url, String mimeType, String charset, byte[] data) {
        // 接收响应数据体
        this(id, dataType, requestId, method, url);
        this.mimeType = mimeType;
        this.charset = charset;
        this.data = data;
    }

    public static HttpData instance(int id, HttpDataType dataType, RequestParams requestParams) {
        return new HttpData(id, dataType, requestParams.getRequestId(), requestParams.getMethod(), requestParams.getURL());
    }

    public static HttpData instance(int id, HttpDataType dataType, RequestParams requestParams, String mimeType, String charset, byte[] data) {
        // 接收响应数据体
        return new HttpData(id, dataType, requestParams.getRequestId(), requestParams.getMethod(), requestParams.getURL(), mimeType, charset, data);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HttpDataType getDataType() {
        return dataType;
    }

    public void setDataType(HttpDataType dataType) {
        this.dataType = dataType;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
