package com.xknower.utils.okhttp.result;

import com.xknower.utils.okhttp.exception.HttpException;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author xknower
 */
public class StringResultExecute implements ResultExecute<String> {

    @Override
    public String toBody(Response response, Request request, StopWatch stopWatch) throws IOException {
        switch (response.code()) {
            case 200:
                return response.body().string();
            default:
                throw HttpException.make(response.code() + " ==> " + response.body().string(), request, stopWatch);
        }
    }
}
