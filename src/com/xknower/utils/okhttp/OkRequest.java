package com.xknower.utils.okhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求体
 *
 * @author xknower
 */
public class OkRequest {

    /**
     * 请求 URL
     */
    private String url;

    private String body;

    /**
     * 请求头设置
     */
    private Map<String, String> heads;

    /**
     * 传递参数
     */
    private Map<String, Object> params = new HashMap<>();

    //

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, String> heads) {
        this.heads = heads;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    //

    public static OkRequest.OkRequestBuilder builder() {
        return new OkRequest.OkRequestBuilder();
    }

    public static class OkRequestBuilder {

        private String url;
        private String body;
        private Map<String, String> heads;
        private Map<String, Object> params;

        OkRequestBuilder() {
        }

        public OkRequest.OkRequestBuilder url(String url) {
            this.url = url;
            return this;
        }

        public OkRequest.OkRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public OkRequest.OkRequestBuilder heads(Map<String, String> heads) {
            this.heads = heads;
            return this;
        }


        public OkRequest.OkRequestBuilder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public OkRequest build() {
            OkRequest okRequest = new OkRequest();
            okRequest.setUrl(url);
            okRequest.setBody(body);
            okRequest.setHeads(heads);
            okRequest.setParams(params);
            return okRequest;
        }

        @Override
        public String toString() {
            return "OkRequest.OkRequestBuilder(url=" + this.url + ", body=" + this.body + ", heads=" + this.heads + ", params=" + this.params + ")";
        }
    }
}
