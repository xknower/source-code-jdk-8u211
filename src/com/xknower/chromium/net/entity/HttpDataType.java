package com.xknower.chromium.net.entity;

/**
 * HTTP 请求数据类型
 */
public enum HttpDataType {

    //
    BeforeRequest(0),
    //
    BeforeRedirect(1),
    //
    BeforeSendHeader(2),
    //
    SendHeader(3),
    //
    HeadersReceived(4),
    //
    ResponseStarted(5),
    //
    DataReceived(6),
    //
    Completed(7),
    //
    Destroyed(8);

    private final int value;

    HttpDataType(int value) {
        this.value = value;
    }

    public static HttpDataType value(int value) {
        for (HttpDataType v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return null;
    }

    public int value() {
        return this.value;
    }
}
