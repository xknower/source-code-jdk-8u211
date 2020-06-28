package com.xknower.utils.httpserver;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * 简单的 HTTP 服务器
 */
public class HttpServer {

    /**
     * WEB_ROOT是HTML和其它文件存放的目录. 这里的WEB_ROOT为工作目录下的webroot目录
     */
    public static final String WEB_ROOT = "";
    //System.getProperty("user.dir") + File.separator + "webroot";

    private boolean start = true;

    /**
     * 关闭服务命令
     */
    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        //等待连接请求
        server.awaitConnections();
    }

    public void awaitConnections() {
        ServerSocket serverSocket = null;
        int port = 80;
        try {
            // 服务器套接字对象
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 循环处理接收的连接
        while (start) {
            Socket socket = null;
            try {
                // 01 等待连接, 连接成功后, 返回一个Socket对象
                socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                // 处理连接
                handleConnection(input, output);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭 socket 对象
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    private void handleConnection(InputStream input, OutputStream output) {
        try {
            // 02 创建Request对象并解析
            Request request = new Request(input);
            request.parse();

            // 检查是否是关闭服务命令
            if (request.getUri().equals("/SHUTDOWN")) {
                start = false;
                return;
            }

            // 03 创建 Response 对象
            Response response = new Response(output);
            response.setRequest(request);
            response.sendStaticResource();
        } catch (Exception e) {
        }
    }
}