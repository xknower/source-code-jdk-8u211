package com.xknower.web.jx.three;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * JxBrowser API provides functionality that allows accessing and modifying POST/PUT/PATCH upload data before
 * it will be sent to a web server. POST/PUT/PATCH upload data can be one of the following types:
 * PLAIN_TEXT
 * BYTES
 * FORM_URL_ENCODED
 * MULTIPART_FORM_DATA
 * <p>
 * Depending on the upload data type, you can use different strategies for accessing and modifying upload data.
 * The following sample demonstrates how to do this:
 * <p>
 * This sample demonstrates how to read and modify POST data of
 * HTTP request using NetworkDelegate.
 */
public class ModifyingPOSTPUTPATCHUploadData {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserContext browserContext = browser.getContext();
        NetworkService networkService = browserContext.getNetworkService();
        networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onBeforeURLRequest(BeforeURLRequestParams params) {
                if ("POST".equals(params.getMethod())) {
                    UploadData uploadData = params.getUploadData();
                    UploadDataType dataType = uploadData.getType();
                    if (dataType == UploadDataType.FORM_URL_ENCODED) {
                        FormData data = (FormData) uploadData;
                        data.setPair("key1", "value1", "value2");
                        data.setPair("key2", "value2");
                    } else if (dataType == UploadDataType.MULTIPART_FORM_DATA) {
                        MultipartFormData data = (MultipartFormData) uploadData;
                        data.setPair("key1", "value1", "value2");
                        data.setPair("key2", "value2");
                        data.setFilePair("file3", "C:\\Test.zip");
                    } else if (dataType == UploadDataType.PLAIN_TEXT) {
                        TextData data = (TextData) uploadData;
                        data.setText("My data");
                    } else if (dataType == UploadDataType.BYTES) {
                        BytesData data = (BytesData) uploadData;
                        data.setData("My data".getBytes());
                    }
                    // Apply modified upload data that will be sent to a web server.
                    params.setUploadData(uploadData);
                }
            }
        });
        browser.loadURL(new LoadURLParams("http://localhost/", "key=value"));
    }
}
