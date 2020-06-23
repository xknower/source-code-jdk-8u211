package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.events.NetError;
import com.xknower.chromium.net.entity.HttpData;
import com.xknower.chromium.net.entity.HttpDataType;
import com.xknower.chromium.net.entity.HttpLifeCycleData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网络资源收集处理器, 获取浏览器请求的所有网络数据
 *
 * @author xknower
 */
public class JxCollectNetworkDelegate extends JxDefaultNetworkDelegate {

    private Map<Long, HttpLifeCycleData> lifeCycleDataMap = new LinkedHashMap<>();

    public Map<Long, HttpLifeCycleData> getLifeCycleDataMap() {
        return lifeCycleDataMap;
    }

    public HttpLifeCycleData getLifeCycleData(Long id) {
        return lifeCycleDataMap.get(id);
    }

    private HttpLifeCycleData lifeCycleData(Long id) {
        HttpLifeCycleData lifeCycleData = lifeCycleDataMap.get(id);
        if (lifeCycleData == null) {
            synchronized (this) {
                if (lifeCycleData == null) {
                    lifeCycleData = new HttpLifeCycleData();
                    lifeCycleDataMap.put(id, lifeCycleData);
                }
            }
        }
        return lifeCycleData;
    }

    /**
     * 发送请求前 [1、获取上传数据; 2、设置上传数据; 3、重置请求URL]
     *
     * @param params [BeforeURLRequestParams -> RequestParams]
     */
    @Override
    public void onBeforeURLRequest(BeforeURLRequestParams params) {
        // 请求上传数据
        UploadData uploadData = params.getUploadData();
        // 请求上传数据类型
        if (uploadData != null) {
            UploadDataType uploadDataType = uploadData.getType();
        }
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.BeforeRequest, params));

//        // 请求上传数据处理
//        if ("POST".equals(params.getMethod()) && uploadData != null) {
//            uploadData = params.getUploadData();
//            UploadDataType dataType = uploadData.getType();
//            if (dataType == UploadDataType.FORM_URL_ENCODED) {
//                FormData formData = (FormData) uploadData;
//            } else if (dataType == UploadDataType.MULTIPART_FORM_DATA) {
//                MultipartFormData multipartFormData = (MultipartFormData) uploadData;
//            } else if (dataType == UploadDataType.PLAIN_TEXT) {
//                TextData textData = (TextData) uploadData;
//            } else if (dataType == UploadDataType.BYTES) {
//                BytesData bytesData = (BytesData) uploadData;
//            }
//            // Apply modified upload data that will be sent to a web server.
//            params.setUploadData(uploadData);
//        }
    }

    /**
     * 发起重定向请求前 [1、获取响应状态编码; 2、获取重定向URL]
     *
     * @param params [BeforeRedirectParams -> RequestParams]
     */
    @Override
    public void onBeforeRedirect(BeforeRedirectParams params) {
        // 重定向数据
        final int responseCode = params.getResponseCode();
        final String newUrl = params.getNewURL();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.BeforeRedirect, params));
    }

    /**
     * 发送请求头前 [1、获取请求头对象 HttpHeaders; 2、(过时)获取请求头对象 HttpHeaders;]
     *
     * @param params [BeforeSendHeadersParams -> RequestParams]
     */
    @Override
    public void onBeforeSendHeaders(BeforeSendHeadersParams params) {
        // 请求头
        final HttpHeadersEx headersEx = params.getHeadersEx();
        final HttpHeaders httpHeaders = params.getHeaders();
        final Map<String, List<String>> headers = headersEx.getHeaders();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.BeforeSendHeader, params));
    }

    /**
     * 发送请求头后 [1、获取请求头对象 HttpHeaders; 2、(过时)获取请求头对象 HttpHeaders;]
     *
     * @param params [SendHeadersParams -> RequestParams]
     */
    @Override
    public void onSendHeaders(SendHeadersParams params) {
        // 请求头
        final HttpHeadersEx headersEx = params.getHeadersEx();
        final HttpHeaders httpHeaders = params.getHeaders();
        final Map<String, List<String>> headers = headersEx.getHeaders();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.SendHeader, params));
    }

    /**
     * 接收响应头 [1、获取请求mimeType; 2、获取请求charset; 3、获取请求statusLine; 4、获取请求contentLength]
     * [5、获取响应头对象 HttpHeaders; 2、(过时)获取响应头对象 HttpHeaders;]
     *
     * @param params [HeadersReceivedParams -> RequestParams]
     */
    @Override
    public void onHeadersReceived(HeadersReceivedParams params) {
        //
        final String mimeType = params.getMimeType();
        final String charset = params.getCharset();
        final StatusLine statusLine = params.getStatusLine();
        final int contentLength = params.getContentLength();
        //
        final HttpHeadersEx headersEx = params.getHeadersEx();
        final HttpHeaders httpHeaders = params.getHeaders();
        final Map<String, List<String>> headers = headersEx.getHeaders();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.HeadersReceived, params));
    }

    /**
     * 接收响应数据体前 [1、获取响应状态编码]
     *
     * @param params [ResponseStartedParams -> RequestParams]
     */
    @Override
    public void onResponseStarted(ResponseStartedParams params) {
        // 响应状态编码
        final int responseCode = params.getResponseCode();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.ResponseStarted, params));
    }

    /**
     * 接收响应数据体 [1、获取响应mimeType; 2、获取响应charset; 3、获取响应数据]
     *
     * @param params [DataReceivedParams -> RequestParams]
     */
    @Override
    public void onDataReceived(DataReceivedParams params) {
        // 响应数据
        final String mimeType = params.getMimeType();
        final String charset = params.getCharset();
        final byte[] data = params.getData();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.DataReceived, params, mimeType, charset, data));
    }

    /**
     * 接收响应完成 [1、获取响应状态编码; 2、获取HTTP请求结果; 3、获取HTTP请求(发生异常时)错误类型]
     *
     * @param params [RequestCompletedParams -> RequestParams]
     */
    @Override
    public void onCompleted(RequestCompletedParams params) {
        //
        final int responseCode = params.getResponseCode();
        final RequestStatus requestStatus = params.getStatus();
        final NetError netError = params.getErrorCode();
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.Completed, params));
    }

    /**
     * 销毁HTTP请求
     *
     * @param params [RequestParams]
     */
    @Override
    public void onDestroyed(RequestParams params) {
        //
        lifeCycleData(params.getRequestId()).setHttpData(HttpData.instance(no(), HttpDataType.Destroyed, params));
    }

    private final static AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    private static int no() {
        return ATOMIC_INTEGER.incrementAndGet() % Short.MAX_VALUE;
    }
}
