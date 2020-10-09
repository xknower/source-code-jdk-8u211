package com.xknower.chromium;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.Callback;
import com.xknower.chromium.view.ViewFrameSwing;
import com.xknower.utils.CallBack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 1、BrowerVisiterManager 浏览器访问功能, 图形展示功能及非图形展示功能
 * // 浏览功能
 * // 浏览器生命周期管理及事件捕获处理、页面生命周期管理及事件捕获处理、资源生命周期管理及事件捕获处理
 * // 页面元素查询及相关操作
 * 2、GraphicInterfaceManager 提供数据的访问和操作、事件的捕获及传递处理
 * // 显示功能
 * // 图形界面管理、键盘管理及其事件处理、鼠标管理及其事件处理
 * <p>
 * 浏览器相关, 事件处理器 BrowserEventManager
 * 网络资源处理器 BrowserResourcesManager
 * 键盘鼠标时间处理器 MouseKeyEventManager
 *
 * @author xknower
 */
public class BrowserEntity {

    public static int remoteDebuggingPort = 9222;

    private boolean isVisible = true;

    private JxBrowser jxBrowser;

    private JxBrowserView jxBrowserView;

    private ViewFrameSwing viewFrameSwing;

    static {
        setChromiumSwitches();
    }

    /**
     * 初始化浏览器及其视图, 上下文环境
     */
    public BrowserEntity() {
        // 创建浏览器
        this.jxBrowser = new JxBrowser();
        // 创建视图
        this.jxBrowserView = new JxBrowserView(this.jxBrowser);
    }

    public BrowserEntity(JxBrowser jxBrowser) {
        // 创建浏览器
        this.jxBrowser = jxBrowser;
        // 创建视图
        this.jxBrowserView = new JxBrowserView(this.jxBrowser);
    }

    public JxBrowser jxBrowser() {
        return jxBrowser;
    }

    public JxBrowserView jxBrowserView() {
        return jxBrowserView;
    }

    public ViewFrameSwing viewFrameSwing() {
        return viewFrameSwing;
    }

    //

    public void setVisible(boolean visible) {
        isVisible = visible;
        if (jxBrowserView() != null) {
            // 关闭渲染
            jxBrowserView().setVisible(isVisible);
        }
        if (viewFrameSwing() != null) {
            // 关闭窗口
            viewFrameSwing().setVisible(isVisible);
        }
    }

    public void setTitle(String title) {
        if (viewFrameSwing() != null) {
            // 关闭窗口
            viewFrameSwing().getFrame().setTitle(title);
        }
    }

    public void setIconImage(Image iconImage) {
        if (viewFrameSwing() != null) {
            // 关闭窗口
            viewFrameSwing().getFrame().setIconImage(iconImage);
        }
    }

    public void setIconImage(String url) {
        if (viewFrameSwing() != null) {
            // 设置窗口图标
            try {
                // 设置窗口图标
                Image image = ImageIO.read(new URL(url));
                setIconImage(image);
            } catch (MalformedURLException ioe) {
            } catch (IOException e) {
            }
        }
    }

    //

    public static void setChromiumSwitches() {
        setChromiumSwitches("--remote-debugging-port=" + remoteDebuggingPort);
    }

    public static void setChromiumSwitches(String... switches) {
        BrowserPreferences.setChromiumSwitches(switches);
    }

    /**
     * 窗口初始化完毕, 回调加载事件
     */
    public BrowserEntity registerFinishLoadingMainFrame(CallBack<JxBrowser, Boolean> registerFinishLoadingMainFrame) {
        // 注册, 加载网页并等待网页完全加载, 在执行注册的回调函数
        JxBrowser.invokeAndWaitFinishLoadingMainFrame(jxBrowser(), new Callback<Browser>() {
            //
            @Override
            public void invoke(Browser browser) {
                if (registerFinishLoadingMainFrame != null) {
                    registerFinishLoadingMainFrame.call(JxBrowser.instance(browser));
                }
            }
        });
        return this;
    }

    /**
     * 部署事件监控及注册相关组件
     */
    public BrowserEntity registerEventHandler(CallBack<JxBrowser, Boolean> registerEventHandler) {
        if (registerEventHandler != null) {
            registerEventHandler.call(jxBrowser());
        }
        return this;
    }

    /**
     * 初始化窗口程序 (根据浏览器视图)
     *
     * @return JFrame
     */
    protected ViewFrameSwing initializeViewFrame() {
        //
        return initializeViewFrame("Main", 500, 0, 1030, 1000);
    }

    public ViewFrameSwing initializeViewFrame(String title, int x, int y, int width, int height) {
        viewFrameSwing = new ViewFrameSwing(title, jxBrowserView.browserViewSwing());
        viewFrameSwing.viewShow(new CallBack<ViewFrameSwing, WindowAdapter>() {
            @Override
            public WindowAdapter call(ViewFrameSwing view) {
                //
                view.getFrame().setLocation(x, y);
                view.getFrame().setSize(width, height);
                //
                return new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // 窗口关闭, 关闭渲染, 销毁浏览器
                        setVisible(false);
                        jxBrowser.dispose();
                    }
                };
            }
        });
        return viewFrameSwing;
    }

    private boolean isRemoteDebuggingEnabled() {
        // Gets URL of the remote Developer Tools web page for browser1 instance.
        for (String k : BrowserPreferences.getChromiumSwitches()) {
            if (k.contains("--remote-debugging-port=")) {
                return true;
            }
            System.out.println("ChromiumSwitches : " + k);
        }
        return false;
    }

    /**
     * 打开远程调试界面
     */
    public BrowserEntity openRemoteDebuggingHandler() {
        if (isRemoteDebuggingEnabled()) {
            BrowserEntity browserEntity = new BrowserEntity();
            //
            browserEntity.initializeViewFrame("DebugFrame", 100, 0, 1030, 1000);
            //
            System.out.println("OpenDebug : " + jxBrowser().getRemoteDebuggingURL());
            browserEntity.jxBrowser().loadURL(jxBrowser().getRemoteDebuggingURL());
            return browserEntity;
        }
        return null;
    }
}
