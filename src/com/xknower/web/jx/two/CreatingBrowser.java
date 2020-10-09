package com.xknower.web.jx.two;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;

import java.io.File;

/**
 * 创建浏览器
 * <p>
 * BrowserContext 浏览器上下文
 * Browser 浏览器
 */
public class CreatingBrowser {

    public void init() {
        //
        Browser A = new Browser();
        Browser A1 = new Browser();
        // BrowserContext.defaultContext（）方法返回默认BrowserContext被配置为存储的Chromium数据文件
        // 具有相同BrowserContext实例的两个Browser实例将使用相同的用户数据目录。结果，他们将共享cookie和缓存文件。
        Browser B = new Browser(BrowserContext.defaultContext());
        // 数据文件存储路径
        System.out.println(BrowserPreferences.getDefaultDataDir());

        Browser B1 = new Browser(BrowserContext.defaultContext());

        // The Chromium profile directory is already used/locked by another BrowserContext instance or process.
        // BrowserException: The Chromium profile directory is already used/locked by another BrowserContext instance or process
        // 要创建不共享Cookie和缓存数据的独立浏览器实例，必须使用配置为使用不同数据目录的不同BrowserContext实例初始化每个浏览器实例。
        // 要获取Browser实例的BrowserContext，可以使用browser.getContext()方法
        // 禁止在单个或多个Java应用程序实例中使用多个配置为使用相同数据目录的BrowserContext实例
        // 在同一台计算机上同时启动多个Java应用程序实例, 那么请不要使用默认构造函数在您的应用程序中创建Browser实例。
        BrowserContext contextA = new BrowserContext(new BrowserContextParams("C:\\my-data1"));
        Browser contextA1 = new Browser(contextA);
        System.out.println(BrowserPreferences.getDefaultDataDir());
        System.out.println(contextA1.getContext().getDataDir());

        //
        String path = new File("user_data_dir").getAbsolutePath();
        BrowserContext browserContext = new BrowserContext(new BrowserContextParams(path));
        Browser browserOne = new Browser(browserContext);
        Browser browserTwo = new Browser(browserContext);

        System.out.println();
    }

    public static void main(String[] args) {
        new CreatingBrowser().init();
    }
}
