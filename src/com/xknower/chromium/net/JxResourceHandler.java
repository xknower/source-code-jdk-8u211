package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.ResourceHandler;
import com.teamdev.jxbrowser.chromium.ResourceParams;

/**
 * 网络资源处理拦截器
 *
 * @author xknower
 */
public class JxResourceHandler implements ResourceHandler {

    /**
     * 是否加载该网络资源
     *
     * @param resourceParams ResourceParams
     * @return true 加载, false 不加载
     */
    @Override
    public boolean canLoadResource(ResourceParams resourceParams) {
//        // Cancel loading of all images
//        String.format("加载资源 => URL : %s, Type : %s", resourceParams.getURL(), resourceParams.getResourceType());
//        boolean isNotImageType = JxResourceType.value(resourceParams.getResourceType()) != JxResourceType.IMAGE;
//        return isNotImageType;
        return true;
    }
}
