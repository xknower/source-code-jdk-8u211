package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.ProtocolHandler;
import com.teamdev.jxbrowser.chromium.URLRequest;
import com.teamdev.jxbrowser.chromium.URLResponse;

/**
 * @author xknower
 */
public interface JxProtocolHandler extends ProtocolHandler {
    /**
     * @param urlRequest URLRequest
     * @return URLResponse
     */
    @Override
    URLResponse onRequest(URLRequest urlRequest);
}
