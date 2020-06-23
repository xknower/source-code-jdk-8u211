package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.ProtocolHandler;
import com.teamdev.jxbrowser.chromium.ProtocolService;
import com.xknower.chromium.JxBrowser;

/**
 * 网络协议服务
 *
 * @author xknower
 */
public class JxProtocolService {

    private ProtocolService protocolService;

    private JxProtocolService(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    /**
     * 创建 协议服务对象
     *
     * @param protocolService 网络协议服务对象
     * @return JxProtocolService
     */
    public static JxProtocolService instance(ProtocolService protocolService) {
        return new JxProtocolService(protocolService);
    }

    public static JxProtocolService instance(JxBrowser jxBrowser) {
        return new JxProtocolService(jxBrowser.getContext().getProtocolService());
    }

    //

    // JxBrowser 6.11 and higher provides the API that allows you to handle URL requests for standard (e.g. HTTP, HTTPS, FTP, etc.)
    // and non-standard (e.g. JAR, MYPROTOCOL, etc.) protocols.
    // The following example demonstrates how to register protocol handler for standard HTTPS protocol and response with custom data:

    /**
     * 注册, 网络协议处理事件
     *
     * @param protocol 协议名称
     * @param handler  ProtocolHandler
     * @return ProtocolHandler
     */
    public ProtocolHandler setProtocolHandler(String protocol, ProtocolHandler handler) {
        return this.protocolService.setProtocolHandler(protocol, handler);
    }

    public ProtocolHandler removeProtocolHandler(String protocol) {
        return this.protocolService.removeProtocolHandler(protocol);
    }
}

//        //
//        protocolService.setProtocolHandler("https", new ProtocolHandler() {
//            @Override
//            public URLResponse onRequest(URLRequest request) {
//                URLResponse response = new URLResponse();
//                String html = "<html><body><p>Hello there!</p></body></html>";
//                response.setData(html.getBytes());
//                response.getHeaders().setHeader("Content-Type", "text/html");
//                return response;
//            }
//        });

//        // You can use the same way to handle custom non-standard protocols (e.g. "noprotocols").
//        //  browser.loadURL("noprotocols://custom-request/");
//        protocolService.setProtocolHandler("noprotocols", new ProtocolHandler() {
//            @Override
//            public URLResponse onRequest(URLRequest request) {
//                URLResponse response = new URLResponse();
//                String html = "<html><body><p>Hello there!</p></body></html>";
//                response.setData(html.getBytes());
//                response.getHeaders().setHeader("Content-Type", "text/html");
//                return response;
//            }
//        });
//
//        // It is also possible to register a custom protocol handler for JAR protocol.
//        // It allows you to load HTML files directly from JAR libraries included into your application class path.
//        // Assume that we need to load a resource related to this class in the JAR file.
//        // browser.loadURL(BrowserResourcesHandler.class.getResource("index.html").toString());
//        protocolService.setProtocolHandler("jar", new ProtocolHandler() {
//            @Override
//            public URLResponse onRequest(URLRequest request) {
//                try {
//                    URLResponse response = new URLResponse();
//                    URL path = new URL(request.getURL());
//                    InputStream inputStream = path.openStream();
//                    DataInputStream stream = new DataInputStream(inputStream);
//                    byte[] data = new byte[stream.available()];
//                    stream.readFully(data);
//                    response.setData(data);
//                    String mimeType = getMimeType(path.toString());
//                    response.getHeaders().setHeader("Content-Type", mimeType);
//                    return response;
//                } catch (Exception ignored) {
//                }
//                return null;
//            }
//
//            private String getMimeType(String path) {
//                if (path.endsWith(".html")) {
//                    return "text/html";
//                }
//                if (path.endsWith(".css")) {
//                    return "text/css";
//                }
//                if (path.endsWith(".js")) {
//                    return "text/javascript";
//                }
//                return "text/html";
//            }
//        });
