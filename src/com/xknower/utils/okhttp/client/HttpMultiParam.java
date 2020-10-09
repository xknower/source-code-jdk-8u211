package com.xknower.utils.okhttp.client;

import okhttp3.MediaType;

import java.io.InputStream;

/**
 * HTTP多参数, 上传文件
 *
 * @author xknower
 */
public class HttpMultiParam {

    private String fileName;

    private InputStream inputStream;

    private MediaType mediaType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
