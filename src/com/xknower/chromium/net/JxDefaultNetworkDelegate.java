package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 网络资源处理器
 *
 * @author xknower
 */
public class JxDefaultNetworkDelegate implements NetworkDelegate {

    /**
     * 发送请求前 [1、获取上传数据; 2、设置上传数据; 3、重置请求URL]
     *
     * @param params [BeforeURLRequestParams -> RequestParams]
     */
    @Override
    public void onBeforeURLRequest(BeforeURLRequestParams params) {
    }

    /**
     * 发起重定向请求前 [1、获取响应状态编码; 2、获取重定向URL]
     *
     * @param params [BeforeRedirectParams -> RequestParams]
     */
    @Override
    public void onBeforeRedirect(BeforeRedirectParams params) {
    }

    /**
     * 发送请求头前 [1、获取请求头对象 HttpHeaders; 2、(过时)获取请求头对象 HttpHeaders;]
     *
     * @param params [BeforeSendHeadersParams -> RequestParams]
     */
    @Override
    public void onBeforeSendHeaders(BeforeSendHeadersParams params) {
    }

    /**
     * 发送请求头后 [1、获取请求头对象 HttpHeaders; 2、(过时)获取请求头对象 HttpHeaders;]
     *
     * @param params [SendHeadersParams -> RequestParams]
     */
    @Override
    public void onSendHeaders(SendHeadersParams params) {
    }

    /**
     * 接收响应头 [1、获取请求mimeType; 2、获取请求charset; 3、获取请求statusLine; 4、获取请求contentLength]
     * [5、获取响应头对象 HttpHeaders; 2、(过时)获取响应头对象 HttpHeaders;]
     *
     * @param params [HeadersReceivedParams -> RequestParams]
     */
    @Override
    public void onHeadersReceived(HeadersReceivedParams params) {
    }

    /**
     * 接收响应数据体前 [1、获取响应状态编码]
     *
     * @param params [ResponseStartedParams -> RequestParams]
     */
    @Override
    public void onResponseStarted(ResponseStartedParams params) {
    }

    /**
     * 接收响应数据体 [1、获取响应mimeType; 2、获取响应charset; 3、获取响应数据]
     *
     * @param params [DataReceivedParams -> RequestParams]
     */
    @Override
    public void onDataReceived(DataReceivedParams params) {
    }

    /**
     * 接收响应完成 [1、获取响应状态编码; 2、获取HTTP请求结果; 3、获取HTTP请求(发生异常时)错误类型]
     *
     * @param params [RequestCompletedParams -> RequestParams]
     */
    @Override
    public void onCompleted(RequestCompletedParams params) {
    }

    /**
     * 销毁HTTP请求
     *
     * @param params [RequestParams]
     */
    @Override
    public void onDestroyed(RequestParams params) {
    }

    // -- 请求配置

    /**
     * HTTP请求前, 鉴权
     *
     * @param params [AuthRequiredParams]
     * @return true
     */
    @Override
    public boolean onAuthRequired(AuthRequiredParams params) {
        // 鉴权参数
        boolean proxy = params.isProxy();
        String url = params.getURL();
        String host = params.getHost();
        int port = params.getPort();
        String userName = params.getUsername();
        String password = params.getPassword();
        String scheme = params.getScheme();
        String realm = params.getRealm();
        Browser browser = params.getBrowser();
        //
        String required = "Authentication Required";
        url = "The server " + url + " requires a username and password.";
        JTextField jTextField = new JTextField();
        JPasswordField jPasswordField = new JPasswordField();
        Object[] paramObj = new Object[]{new JLabel(url), Box.createVerticalStrut(10), new JLabel("User Name:"), jTextField, new JLabel("Password:"), jPasswordField};
        if (JOptionPane.showConfirmDialog((Component) null, paramObj, required, 2, -1) == 0) {
            params.setUsername(jTextField.getText());
            params.setPassword(new String(jPasswordField.getPassword()));
            return false;
        } else {
            return true;
        }
    }

    /**
     * HTTP请求配置, 禁止/筛选所有传出的cookie
     * <p>
     * // Using Network API you can control all incoming and outgoing cookies.
     * // You can enable/disable saving/sending cookies.
     * // Using the NetworkDelegate.onCanSetCookies(String url, List<Cookie> cookies) method you can decide whether cookies should be saved or not.
     * // To disable sending some cookies to a web server you can use the NetworkDelegate.onCanGetCookies(String url, List<Cookie> cookies) method.
     * // Suppress/filter all incoming and outgoing cookies.
     * // 禁止/筛选所有传入和传出的cookie。
     *
     * @param url     URL
     * @param cookies Cookies
     * @return false 禁止, true 不禁止
     */
    @Override
    public boolean onCanSetCookies(String url, List<Cookie> cookies) {
        return true;
    }

    /**
     * HTTP请求配置, 禁止/筛选所有传入的cookie
     *
     * @param url     URL
     * @param cookies Cookies
     * @return false 禁止, true 不禁止
     */
    @Override
    public boolean onCanGetCookies(String url, List<Cookie> cookies) {
        return true;
    }

    /**
     * HTTP请求配置, 代理配置
     *
     * @param params [BeforeSendProxyHeadersParams -> RequestParams]
     */
    @Override
    public void onBeforeSendProxyHeaders(BeforeSendProxyHeadersParams params) {
    }

    /**
     * HTTP请求配置, PACScript运行错误处理
     *
     * @param params [PACScriptErrorParams]
     */
    @Override
    public void onPACScriptError(PACScriptErrorParams params) {
    }
}
