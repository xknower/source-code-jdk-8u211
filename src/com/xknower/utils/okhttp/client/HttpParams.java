package com.xknower.utils.okhttp.client;

import com.alibaba.fastjson.JSON;
import com.xknower.utils.okhttp.exception.HttpException;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * HTTP参数
 *
 * @author xknower
 */
public class HttpParams {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain");

    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("application/oct-stream");

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求参数, 提交数据参数
     */
    private Map<String, Object> params;

    /**
     * 是否, 提交文本数据 [MEDIA_TYPE_STRING -> text/plain]
     */
    private boolean string;

    /**
     * 提交文本数据时, 提交文本数据内容
     */
    private String stringBody;

    /**
     * 是否, 提交JSON数据 [MEDIA_TYPE_JSON -> application/json; charset=utf-8]
     */
    private boolean json;

    /**
     * 是否, 上传文件
     */
    private boolean file;

    /**
     * User-Agent 设置
     */
    private String userAgent;

    /**
     * 请求头设置
     */
    private Map<String, String> heads;

    public HttpParams put(String name, Object value) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put(name, value);
        return this;
    }

    public Request toRequest() {
        Request.Builder request;
        switch (method.toUpperCase()) {
            case "GET":
                request = this.toGet();
                break;
            case "POST":
                request = this.toPost();
                break;
            default:
                throw new HttpException("not support => " + method);
        }
        //
        if (userAgent != null && !"".equals(userAgent)) {
            request.addHeader("User-Agent", userAgent);
        }
        //
        if (heads != null) {
            heads.forEach((k, v) -> {
                request.addHeader(k, v);
            });
        }
        return request.build();
    }

    /**
     * GET 提交请求
     */
    private Request.Builder toGet() {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            params.forEach((k, v) -> {
                if (v == null) {
                    return;
                }
                builder.addEncodedQueryParameter(k, v.toString());
            });
        }
        return new Request.Builder().url(builder.build());
    }

    /**
     * POST 提交请求
     */
    private Request.Builder toPost() {
        // string
        if (string) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_STRING, stringBody == null ? "" : stringBody);
            return new Request.Builder().url(url).post(body);
        }
        // json提交
        if (json) {
            String jsonString = JSON.toJSONString(params);
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, jsonString);
            return new Request.Builder().url(url).post(body);
        }
        // 上传文件
        else if (file) {
            //
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            params.forEach((k, v) -> {
                if (v == null) {
                    return;
                }
                if (v instanceof File) {
                    File f = (File) v;
                    builder.addFormDataPart(
                            k, f.getName(), RequestBody.create(MEDIA_TYPE_FILE, f)
                    );
                } else if (v instanceof InputStream) {
                    builder.addFormDataPart(
                            k, k, create(MEDIA_TYPE_FILE, (InputStream) v)
                    );
                } else if (v instanceof HttpMultiParam) {
                    HttpMultiParam p = (HttpMultiParam) v;
                    builder.addFormDataPart(
                            k, p.getFileName(), create(p.getMediaType(), p.getInputStream())
                    );
                }
                //
                else {
                    builder.addFormDataPart(k, v.toString());
                }
            });
            return new Request.Builder().url(url).post(builder.build());
        }
        // 上传
        else {
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                params.forEach((k, v) -> {
                    if (v == null) {
                        return;
                    }
                    builder.addEncoded(k, v.toString());
                });
            }
            return new Request.Builder().url(url).post(builder.build());
        }
    }

    /**
     * 创建请求体并设置数据请求体数据
     *
     * @param mediaType   请求体类型
     * @param inputStream 请求体数据
     * @return RequestBody
     */
    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                }
            }
        };
    }

    //

    public static HttpParams get(String url) {
        return HttpParams
                .builder()
                .url(url)
                .method("GET")
                .params(new TreeMap<>())
                .build();
    }

    public static HttpParams get(String url, Map<String, Object> params) {
        return HttpParams
                .builder()
                .url(url)
                .params(params)
                .method("GET")
                .build();
    }

    public static HttpParams post(String url, Map<String, Object> params) {
        return HttpParams
                .builder()
                .url(url)
                .params(params)
                .method("POST")
                .build();
    }

    public static HttpParams postFile(String url, Map<String, Object> params) {
        return HttpParams
                .builder()
                .url(url)
                .params(params)
                .method("POST")
                .file(true)
                .build();
    }

    /**
     * POST 请求
     *
     * @param url  请求地址
     * @param body 请求体提交文本内容
     * @return HttpParams
     */
    public static HttpParams postString(String url, String body) {
        return HttpParams
                .builder()
                .url(url)
                .string(true)
                .stringBody(body)
                .method("POST")
                .build();
    }

    public static HttpParams postJson(String url, Map<String, Object> params) {
        return HttpParams
                .builder()
                .url(url)
                .params(params)
                .method("POST")
                .json(true)
                .build();
    }

    public HttpParams putHead(String k, String v) {
        if (heads == null) {
            heads = new TreeMap<>();
        }
        heads.put(k, v);
        return this;
    }

    //

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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public boolean isString() {
        return string;
    }

    public void setString(boolean string) {
        this.string = string;
    }

    public String getStringBody() {
        return stringBody;
    }

    public void setStringBody(String stringBody) {
        this.stringBody = stringBody;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, String> heads) {
        this.heads = heads;
    }

    //

    public HttpParams(String method, String url, boolean string, String stringBody, Map<String, Object> params, boolean json, boolean file, String userAgent, Map<String, String> heads) {
        this.method = method;
        this.url = url;
        this.string = string;
        this.stringBody = stringBody;
        this.params = params;
        this.json = json;
        this.file = file;
        this.userAgent = userAgent;
        this.heads = heads;
    }

    public static HttpParams.HttpParamsBuilder builder() {
        return new HttpParams.HttpParamsBuilder();
    }

    public static class HttpParamsBuilder {
        private String method;
        private String url;
        private boolean string;
        private String stringBody;
        private Map<String, Object> params;
        private boolean json;
        private boolean file;
        private String userAgent;
        private Map<String, String> heads;

        HttpParamsBuilder() {
        }

        public HttpParams.HttpParamsBuilder method(String method) {
            this.method = method;
            return this;
        }

        public HttpParams.HttpParamsBuilder url(String url) {
            this.url = url;
            return this;
        }

        public HttpParams.HttpParamsBuilder string(boolean string) {
            this.string = string;
            return this;
        }

        public HttpParams.HttpParamsBuilder stringBody(String stringBody) {
            this.stringBody = stringBody;
            return this;
        }

        public HttpParams.HttpParamsBuilder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public HttpParams.HttpParamsBuilder json(boolean json) {
            this.json = json;
            return this;
        }

        public HttpParams.HttpParamsBuilder file(boolean file) {
            this.file = file;
            return this;
        }

        public HttpParams.HttpParamsBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public HttpParams.HttpParamsBuilder heads(Map<String, String> heads) {
            this.heads = heads;
            return this;
        }

        public HttpParams build() {
            return new HttpParams(this.method, this.url, this.string, this.stringBody, this.params, this.json, this.file, this.userAgent, this.heads);
        }

        @Override
        public String toString() {
            return "HttpParams.HttpParamsBuilder(method=" + this.method + ", url=" + this.url + ", string=" + this.string + ", stringBody=" + this.stringBody + ", params=" + this.params + ", json=" + this.json + ", file=" + this.file + ", userAgent=" + this.userAgent + ", heads=" + this.heads + ")";
        }
    }
}
