package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.CertificateVerifier;
import com.teamdev.jxbrowser.chromium.NetworkDelegate;
import com.teamdev.jxbrowser.chromium.NetworkService;
import com.teamdev.jxbrowser.chromium.ResourceHandler;
import com.xknower.chromium.JxBrowser;

/**
 * 网络服务 [NetworkService]
 *
 * @author xknower
 */
public class JxNetworkService {

    private NetworkService networkService;

    private JxNetworkService(NetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * 创建 网络服务对象
     *
     * @param networkService 网络服务对象
     * @return JxNetworkService
     */
    public static JxNetworkService instance(NetworkService networkService) {
        return new JxNetworkService(networkService);
    }

    public static JxNetworkService instance(JxBrowser jxBrowser) {
        return new JxNetworkService(jxBrowser.getContext().getNetworkService());
    }

    //

    /**
     * 注册, 网络资源处理器
     * <p>
     * JxBrowser API provides functionality that allows accessing HTTP response data
     * such as HTML, plain text, JavaScript code, CSS, images, etc.
     * JxBrowser API提供了允许访问HTTP响应数据（如HTML、纯文本、JavaScript代码、CSS、图像等）的功能。
     * Using this functionality you can capture AJAX response body content with information about its mime type.
     * 使用此功能，您可以捕获包含其mime类型信息的AJAX响应正文内容
     *
     * @param delegate 网络资源处理器
     */
    public final void setNetworkDelegate(JxDefaultNetworkDelegate delegate) {
        this.networkService.setNetworkDelegate(delegate);
    }

    public final NetworkDelegate getNetworkDelegate() {
        return this.networkService.getNetworkDelegate();
    }

    public final void setCertificateVerifier(CertificateVerifier verifier) {
        this.networkService.setCertificateVerifier(verifier);
    }

    public final CertificateVerifier getCertificateVerifier() {
        return this.networkService.getCertificateVerifier();
    }

    /**
     * Using ResourceHandler you can determine whether resources such as HTML, Images, JavaScript & CSS files, favicon, etc.
     * 使用ResourceHandler，您可以确定诸如HTML、图像、JavaScript和CSS文件、favicon等资源
     * should be loaded or not. By default all resources are loaded. 是否应加载。默认情况下，将加载所有资源。
     * To modify default behavior you need to register your own ResourceHandler implementation.
     * 要修改默认行为，需要注册自己的ResourceHandler实现。
     *
     * @param handler ResourceHandler
     */
    public final void setResourceHandler(JxResourceHandler handler) {
        this.networkService.setResourceHandler(handler);
    }

    public final ResourceHandler getResourceHandler() {
        return this.networkService.getResourceHandler();
    }

    public final void setServerWhiteList(String serverWhiteList) {
        this.networkService.setServerWhiteList(serverWhiteList);
    }

    public final String getServerWhiteList() {
        return this.networkService.getServerWhiteList();
    }

    public final void setDelegateWhiteList(String delegateWhiteList) {
        this.networkService.setDelegateWhiteList(delegateWhiteList);
    }

    public final String getDelegateWhiteList() {
        return this.networkService.getDelegateWhiteList();
    }
}
