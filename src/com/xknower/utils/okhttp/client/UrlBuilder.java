package com.xknower.utils.okhttp.client;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author xknower
 */
public class UrlBuilder {

    private String url;

    private Map<String, Object> map;

    public UrlBuilder(String url, Map<String, Object> map) {
        this.url = url;
        this.map = map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append(url);
        if (!url.contains("?")) {
            sb.append("?");
        }
        //
        map.forEach((k, v) -> {
            try {
                sb
                        .append(k)
                        .append("=")
                        .append(URLEncoder.encode(v.toString(), "utf-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
            }
        });
        sb.trimToSize();
        return sb.subSequence(0, sb.length() - 1).toString();
    }
}
