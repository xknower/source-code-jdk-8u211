package com.xknower.utils.okhttp;

import com.xknower.utils.okhttp.client.HttpClient;
import com.xknower.utils.okhttp.client.HttpClientConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class OkMain {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClientConfig.instance().httpClient();

        XkDfsService okHttpExecuteHandler = new XkDfsService(httpClient);

        // 上传文件
        // filename 文件名, path 上传路径 , file 文件内容, output=json
        Map<String, Object> params = new HashMap<>();
        params.put("filename", "xx1");
        params.put("path", "group");
        params.put("file", new File("D:\\tmpdata\\xx.jpeg"));
        params.put("output", "json");
        OkResponse okResponse =
                okHttpExecuteHandler
                        .callPostFile(OkRequest.builder().url("http://127.0.0.1:8000/group/upload")
                                .params(params)
                                .build());
        // {"url":"http://127.0.0.1:8000/group/group/xx1","md5":"52bdc08faa954967d213cc277aead651","path":"/group/group/xx1","src":"/group/group/xx1","domain":"http://127.0.0.1:8000","scene":"default","size":409173,"mtime":1593012659,"scenes":"default","retmsg":"","retcode":0}
        System.out.println(okResponse.getBody());
    }
}
