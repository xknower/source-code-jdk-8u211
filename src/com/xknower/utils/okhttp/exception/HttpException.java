package com.xknower.utils.okhttp.exception;

import com.xknower.utils.okhttp.result.StopWatch;
import okhttp3.Request;

/**
 * HTTP 异常
 *
 * @author xknower
 */
public class HttpException extends RuntimeException {

    private Request request;

    private StopWatch stopWatch;

    public HttpException() {
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static HttpException make(String msg, Request req, StopWatch s) {
        HttpException exception = new HttpException(msg);
        //
        exception.setRequest(req);
        exception.setStopWatch(s);
        return exception;
    }

    public static HttpException make(Exception e, Request req, StopWatch s) {
        HttpException exception = new HttpException(e);
        exception.setRequest(req);
        exception.setStopWatch(s);
        return exception;
    }

    @Override
    public HttpException fillInStackTrace() {
        return this;
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
}
