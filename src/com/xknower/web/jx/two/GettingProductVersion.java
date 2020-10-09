package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.ProductInfo;

/**
 * This sample demonstrates how to get JxBrowser version and build.
 */
public class GettingProductVersion {
    public static void main(String[] args) {
        System.out.println("JxBrowser version: " + ProductInfo.getVersion());
    }
}
