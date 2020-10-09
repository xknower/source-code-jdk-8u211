package com.xknower.chromium;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMNodeAtPoint;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.xknower.chromium.event.*;
import com.xknower.chromium.net.JxNetworkService;
import com.xknower.chromium.view.JxPopupContainer;
import com.xknower.utils.CallBack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 封装 {@link com.teamdev.jxbrowser.chromium.Browser} 浏览器功能
 * <p>
 * BrowserPreferences 浏览器属性, 可以设置浏览器实体属性, 启用警用某些功能
 *
 * @author xknower
 */
public class JxBrowser {

    private Browser browser;

    private JxNetworkService jxNetworkService;

    public JxBrowser() {
        browser = new Browser();
    }

    public JxBrowser(Browser browser) {
        this.browser = browser;
    }

    /**
     * BrowserContext 浏览器上下文环境 (禁止在单个或多个Java应用程序实例中使用多个配置为使用相同数据目录的BrowserContext实例)
     *
     * <p>
     * BrowserContextParams 缓存存储位置和方式, 设置存在在缓存中时为匿名模式
     *
     * @param browserContext
     */
    public JxBrowser(BrowserContext browserContext) {
        this.browser = new Browser(browserContext);
    }

    public JxBrowser(BrowserType browserType) {
        this.browser = new Browser(browserType);
    }

    public JxBrowser(BrowserType browserType, BrowserContext browserContext) {
        this.browser = new Browser(browserType, browserContext);
    }

    public static JxBrowser instance(Browser browser) {
        return new JxBrowser(browser);
    }


    //
    public Browser browser() {
        return this.browser;
    }

    public BrowserType getType() {
        return this.browser.getType();
    }

    public void setSize(int width, int height) {
        this.browser.setSize(width, height);
    }

    public JxNetworkService jxNetworkService() {
        if (jxNetworkService == null) {
            jxNetworkService = JxNetworkService.instance(this);
        }
        return jxNetworkService;
    }

    //

    /**
     * 标题变更事件监听处理器
     *
     * @param listener
     */
    public void addTitleListener(JxTitleListener listener) {
        this.browser.addTitleListener(listener);
    }

    public void removeTitleListener(TitleListener listener) {
        this.browser.removeTitleListener(listener);
    }

    public List<TitleListener> getTitleListeners() {
        return this.browser.getTitleListeners();
    }

    /**
     * API provides functionality that allows you to get notifications about status bar text changes.
     * <p>
     * 状态事件, 监听处理器
     *
     * @param listener
     */
    public void addStatusListener(JxStatusListener listener) {
        this.browser.addStatusListener(listener);
    }

    /**
     * 移除状态变更监听器
     *
     * @param listener
     */
    public void removeStatusListener(StatusListener listener) {
        this.browser.removeStatusListener(listener);
    }

    /**
     * 获取所有的状态变更监听器
     *
     * @return
     */
    public List<StatusListener> getStatusListeners() {
        return this.browser.getStatusListeners();
    }

    /**
     * 添加加载事件处理器, 浏览器生命周期状态事件监控处理
     *
     * @param listener LoadListener
     */
    public void addLoadListener(JxLoadListener listener) {
        this.browser.addLoadListener(listener);
    }

    /**
     * 移除加载监听器
     */
    public void removeLoadListener(LoadListener listener) {
        this.browser.removeLoadListener(listener);
    }

    /**
     * 获取所有加载监听器
     */
    public List<LoadListener> getLoadListeners() {
        return this.browser.getLoadListeners();
    }

    /**
     * 添加, ScriptContext 事件监听处理器 (执行所有JavaScript代码的上下文)
     *
     * @param listener ScriptContext 监听器
     */
    public void addScriptContextListener(ScriptContextListener listener) {
        this.browser.addScriptContextListener(listener);
    }

    /**
     * 移除  ScriptContext 事件监听处理器
     */
    public void removeScriptContextListener(ScriptContextListener listener) {
        this.browser.removeScriptContextListener(listener);
    }

    /**
     * 获取 ScriptContext 事件监听处理器
     */
    public List<ScriptContextListener> getScriptContextListeners() {
        return this.browser.getScriptContextListeners();
    }

    /**
     * 渲染过程事件
     * <p>
     * Each Browser instance is running in a separate native process where the web page is rendered.
     * // 每个浏览器实例都在一个单独的本机进程中运行，在该进程中呈现网页
     * Sometimes this process can exit unexpectedly because of the crash in plugin.
     * // 有时这个进程会因为插件崩溃而意外退出。
     * To receive notifications about unexpected render process termination you can use RenderListener.
     * // 要接收有关意外呈现进程终止的通知，可以使用RenderListener。
     * When you receive notification about render process termination you can display a "sad" icon like Google Chrome does,
     * for example, to inform the user that this particular Browser component has crashed.
     * // 当您收到有关渲染进程终止的通知时，您可以像Google Chrome那样显示一个“sad”图标，例如，通知用户这个特定的浏览器组件已经崩溃
     * <p>
     * RenderAdapter
     *
     * @param listener RenderListener
     */
    public void addRenderListener(JxRenderListener listener) {
        this.browser.addRenderListener(listener);
    }

    /**
     * 移除渲染监听器
     */
    public void removeRenderListener(RenderListener listener) {
        this.browser.removeRenderListener(listener);
    }

    /**
     * 获取渲染监听器
     */
    public List<RenderListener> getRenderListeners() {
        return this.browser.getRenderListeners();
    }

    /**
     * 添加控制台监听器, 控制信息输出事件
     *
     * @param listener ConsoleListener
     */
    public void addConsoleListener(JxConsoleListener listener) {
        this.browser.addConsoleListener(listener);
    }

    /**
     * 移除控制台监听器
     */
    public void removeConsoleListener(ConsoleListener listener) {
        this.browser.removeConsoleListener(listener);
    }

    /**
     * 获取所有控制台监听器
     */
    public List<ConsoleListener> getConsoleListeners() {
        return this.browser.getConsoleListeners();
    }

    public void setFullScreenHandler(FullScreenHandler handler) {
        this.browser.setFullScreenHandler(handler);
    }

    public FullScreenHandler getFullScreenHandler() {
        return this.browser.getFullScreenHandler();
    }

    /**
     * 添加弹出窗口(子窗口)事件处理器, 实现子浏览器处理
     *
     * @param handle PopupHandler 返回空拦截弹出窗口, 不在弹出
     */
    private void setPopupHandler(PopupHandler handle) {
        this.browser.setPopupHandler(handle);
    }

    // 子窗口弹出处理 -> Swing
    public void setPopupHandler(CallBack<JxPopupContainer, Boolean> callBack) {
        // 02 浏览器, 外部加载资源, 并弹出子窗口且创建新的浏览器实例
        setPopupHandler(new PopupHandler() {
            @Override
            public PopupContainer handlePopup(PopupParams popupParams) {
                //
                return new PopupContainer() {
                    @Override
                    public void insertBrowser(final Browser browser, final Rectangle initialBounds) {
                        JxPopupContainer popupContainer = new JxPopupContainer(popupParams, new JxBrowser(browser), initialBounds);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                Boolean flag = callBack.call(popupContainer);
                                if (flag == null || !flag) {
                                    //
                                    Rectangle rectangle = popupContainer.getInitialBounds();
                                    rectangle.x = rectangle.x == 0 ? 500 : rectangle.x;
                                    rectangle.y = rectangle.y == 0 ? 100 : rectangle.y;
                                    rectangle.width = rectangle.width == 0 ? 1030 : rectangle.width;
                                    rectangle.height = rectangle.height == 0 ? 1000 : rectangle.height;
                                    //
                                    BrowserView browserView = new BrowserView(popupContainer.getJxBrowser().browser());
                                    browserView.setPreferredSize(popupContainer.getInitialBounds().getSize());
                                    //
                                    final JFrame frame = new JFrame(popupContainer.getPopupParams().getTargetName());
                                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                    frame.add(browserView, BorderLayout.CENTER);
                                    frame.pack();
                                    frame.setLocation(popupContainer.getInitialBounds().getLocation());
                                    frame.setVisible(true);
                                    //
                                    frame.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            popupContainer.getJxBrowser().browser().dispose();
                                        }
                                    });
                                    popupContainer.getJxBrowser().browser().addDisposeListener(new DisposeListener<Browser>() {
                                        @Override
                                        public void onDisposed(DisposeEvent<Browser> event) {
                                            frame.setVisible(false);
                                        }
                                    });
                                }
                            }
                        });
                    }
                };
            }
        });

    }

    /**
     * 获取弹出窗口(子窗口)事件处理器
     */
    public PopupHandler getPopupHandler() {
        return this.browser.getPopupHandler();
    }

    public void setContextMenuHandler(ContextMenuHandler handler) {
        this.browser.setContextMenuHandler(handler);
    }

    public ContextMenuHandler getContextMenuHandler() {
        return this.browser.getContextMenuHandler();
    }

    public void setDownloadHandler(DownloadHandler handler) {
        this.browser.setDownloadHandler(handler);
    }

    public DownloadHandler getDownloadHandler() {
        return this.browser.getDownloadHandler();
    }

    public void setPermissionHandler(PermissionHandler handler) {
        this.browser.setPermissionHandler(handler);
    }

    public PermissionHandler getPermissionHandler() {
        return this.browser.getPermissionHandler();
    }

    public void setDialogHandler(DialogHandler handler) {
        this.browser.setDialogHandler(handler);
    }

    public DialogHandler getDialogHandler() {
        return this.browser.getDialogHandler();
    }

    /**
     * 获取浏览器Cookie缓存存储对象
     */
    public CookieStorage getCookieStorage() {
        return this.browser.getCookieStorage();
    }

    /**
     * 获取浏览器Cache缓存出队对象
     */
    public CacheStorage getCacheStorage() {
        return this.browser.getCacheStorage();
    }

    public synchronized boolean isCommandEnabled(EditorCommand command) {
        return this.browser.isCommandEnabled(command);
    }

    public synchronized boolean executeCommand(EditorCommand command) {
        return this.browser.executeCommand(command);
    }

    public synchronized boolean executeCommand(long frameId, EditorCommand command) {
        return this.browser.executeCommand(frameId, command);
    }

    public synchronized boolean executeCommand(EditorCommand command, String value) {
        return this.browser.executeCommand(command, value);
    }

    public synchronized boolean executeCommand(long frameId, EditorCommand command, String value) {
        return this.browser.executeCommand(frameId, command, value);
    }

    /**
     * 设置浏览器加载事件处理器, 浏览器内部加载资源事件处理
     *
     * @param handler LoadHandler
     */
    public void setLoadHandler(JxLoadHandler handler) {
        this.browser.setLoadHandler(handler);
    }

    /**
     * 获取浏览器加载事件处理器
     */
    public LoadHandler getLoadHandler() {
        return this.browser.getLoadHandler();
    }

    /**
     * 获取浏览器远程调试URL
     */
    public synchronized String getRemoteDebuggingURL() {
        return this.browser.getRemoteDebuggingURL();
    }

    public synchronized List<Long> getFramesIds() {
        return this.browser.getFramesIds();
    }

    public synchronized List<Long> getFramesIds(long frameId) {
        return this.browser.getFramesIds(frameId);
    }

    public synchronized SearchResult findText(SearchParams params) {
        return this.browser.findText(params);
    }

    public synchronized void stopFindingText(StopFindAction action) {
        this.browser.stopFindingText(action);
    }

    public synchronized String getSelectedText() {
        return this.browser.getSelectedText();
    }

    public synchronized String getSelectedText(long frameId) {
        return this.browser.getSelectedText(frameId);
    }

    public synchronized String getSelectedHTML() {
        return this.browser.getSelectedHTML();
    }

    public synchronized String getSelectedHTML(long frameId) {
        return this.browser.getSelectedHTML(frameId);
    }

    public PluginManager getPluginManager() {
        return this.browser.getPluginManager();
    }

    public MediaStreamDeviceManager getMediaStreamDeviceManager() {
        return this.browser.getMediaStreamDeviceManager();
    }

    public void setPrintHandler(PrintHandler handler) {
        this.browser.setPrintHandler(handler);
    }

    public PrintHandler getPrintHandler() {
        return this.browser.getPrintHandler();
    }

    public synchronized void print() {
        this.browser.print();
    }

    public synchronized void print(long frameId) {
        this.browser.print(frameId);
    }

    /**
     * 更新浏览器属性
     *
     * @param preferences
     */
    public synchronized void setPreferences(BrowserPreferences preferences) {
        this.browser.setPreferences(preferences);
    }

    /**
     * 获取浏览器属性
     *
     * @return com.teamdev.jxbrowser.chromium.BrowserPreferences
     */
    public synchronized BrowserPreferences getPreferences() {
        return this.browser.getPreferences();
    }

    public synchronized boolean saveWebPage(String filePath, String dirPath, SavePageType saveType) {
        return this.browser.saveWebPage(filePath, dirPath, saveType);
    }

    public BrowserContext getContext() {
        return this.browser.getContext();
    }

    public synchronized DOMDocument getDocument() {
        return this.browser.getDocument();
    }

    public synchronized DOMDocument getDocument(long frameId) {
        return this.browser.getDocument(frameId);
    }

    public synchronized boolean isSandboxEnabled() {
        return this.browser.isSandboxEnabled();
    }

    public synchronized DOMNodeAtPoint getNodeAtPoint(int x, int y) {
        return this.browser.getNodeAtPoint(x, y);
    }

    public synchronized DOMNodeAtPoint getNodeAtPoint(Point point) {
        return this.browser.getNodeAtPoint(point);
    }

    /**
     * Dispose Browser instance
     * <p>
     * 释放浏览器实例
     */
    public synchronized void dispose() {
        this.browser.dispose();
    }

    public synchronized boolean isDisposed() {
        return this.browser.isDisposed();
    }

    /**
     * 浏览器销毁通知事件, 收到通知时, 浏览器已经销毁 [DisposeEvent -> ]
     * <p>
     * Each Browser instance can be also disposed from JavaScript via the window.close() function.
     * In this case you might be interested in receiving notification when a Browser instance is disposed.
     * To get such notifications, you can use DisposeListener.
     *
     * @param listener DisposeListener
     */
    private void addDisposeListener(DisposeListener<Browser> listener) {
        this.browser.addDisposeListener(listener);
    }

    public void addDisposeListener(CallBack<JxBrowser, Boolean> callBack) {
        addDisposeListener(new DisposeListener<Browser>() {
            /**
             * 浏览器销毁处理
             *
             * @param disposeEvent 销毁之间
             */
            @Override
            public void onDisposed(DisposeEvent disposeEvent) {
                Browser browser = (Browser) disposeEvent.getSource();
                // 浏览器销毁
                callBack.call(JxBrowser.instance(browser));
            }
        });
    }

    public void removeDisposeListener(DisposeListener<Browser> listener) {
        this.browser.removeDisposeListener(listener);
    }

    public List<DisposeListener<Browser>> getDisposeListeners() {
        return this.browser.getDisposeListeners();
    }

    public synchronized void loadURL(String url) {
        this.browser.loadURL(url);
    }

    public synchronized void loadURL(long frameId, String url) {
        this.browser.loadURL(frameId, url);
    }

    public synchronized void loadURL(LoadURLParams params) {
        this.browser.loadURL(params);
    }

    public synchronized void loadHTML(String html) {
        this.browser.loadHTML(html);
    }

    public synchronized void loadHTML(long frameId, String html) {
        this.browser.loadHTML(frameId, html);
    }

    public synchronized void loadHTML(LoadHTMLParams params) {
        this.browser.loadHTML(params);
    }

    public synchronized void loadHTML(long frameId, LoadHTMLParams params) {
        this.browser.loadHTML(frameId, params);
    }

    public synchronized void loadData(LoadDataParams params) {
        this.browser.loadData(params);
    }

    public synchronized void loadData(long frameId, LoadDataParams params) {
        this.browser.loadData(frameId, params);
    }

    public synchronized String getHTML() {
        return this.browser.getHTML();
    }

    public synchronized String getHTML(long frameId) {
        return this.browser.getHTML(frameId);
    }

    public synchronized String getURL() {
        return this.browser.getURL();
    }

    /**
     * 执行JS代码
     *
     * @param javaScript JS
     */
    public synchronized void executeJavaScript(String javaScript) {
        this.browser.executeJavaScript(javaScript);
    }

    public JSValue executeJavaScriptAndReturnValue(String javaScript) {
        return this.browser.executeJavaScriptAndReturnValue(javaScript);
    }

    /**
     * 在指定 窗口 执行JS代码
     *
     * @param frameId    浏览器中的窗口编号
     * @param javaScript JS
     */
    public synchronized void executeJavaScript(long frameId, String javaScript) {
        this.browser.executeJavaScript(frameId, javaScript);
    }

    /**
     * 在指定 窗口 执行JS代码, 并返回 JSValue
     *
     * @param frameId    浏览器中的窗口编号
     * @param javaScript JS
     */
    public JSValue executeJavaScriptAndReturnValue(long frameId, String javaScript) {
        return this.browser.executeJavaScriptAndReturnValue(frameId, javaScript);
    }

    public JSContext getJSContext() {
        return this.browser.getJSContext();
    }

    public JSContext getJSContext(long frameId) {
        return this.browser.getJSContext(frameId);
    }

    public synchronized String getTitle() {
        return this.browser.getTitle();
    }

    public synchronized void setZoomLevel(double zoomLevel) {
        this.browser.setZoomLevel(zoomLevel);
    }

    public synchronized double getZoomLevel() {
        return this.browser.getZoomLevel();
    }

    public synchronized void zoomIn() {
        this.browser.zoomIn();
    }

    public synchronized void zoomOut() {
        this.browser.zoomOut();
    }

    public synchronized void zoomReset() {
        this.browser.zoomReset();
    }

    public synchronized void setZoomEnabled(boolean enabled) {
        this.browser.setZoomEnabled(enabled);
    }

    public synchronized boolean isZoomEnabled() {
        return this.browser.isZoomEnabled();

    }

    public synchronized boolean isLoading() {
        return this.browser.isLoading();
    }

    public synchronized void stop() {
        this.browser.stop();
    }

    public synchronized void goBack() {
        this.browser.goBack();
    }

    public synchronized boolean canGoBack() {
        return this.browser.canGoBack();
    }

    public synchronized void goForward() {
        this.browser.goForward();
    }

    public synchronized boolean canGoForward() {
        return this.browser.canGoForward();
    }

    public synchronized boolean canGoToOffset(int offset) {
        return this.browser.canGoToOffset(offset);
    }

    public synchronized void goToOffset(int offset) {
        this.browser.goToOffset(offset);
    }

    public synchronized void goToIndex(int index) {
        this.browser.goToIndex(index);
    }

    public synchronized int getNavigationEntryCount() {
        return this.browser.getNavigationEntryCount();
    }

    public synchronized int getCurrentNavigationEntryIndex() {
        return this.browser.getCurrentNavigationEntryIndex();
    }

    public synchronized NavigationEntry getCurrentNavigationEntry() {
        return this.browser.getCurrentNavigationEntry();
    }

    public synchronized NavigationEntry getNavigationEntryAtIndex(int index) {
        return this.browser.getNavigationEntryAtIndex(index);
    }

    public synchronized NavigationEntry getNavigationEntryAtOffset(int offset) {
        return this.browser.getNavigationEntryAtOffset(offset);
    }

    public synchronized boolean removeNavigationEntryAtIndex(int index) {
        return this.browser.removeNavigationEntryAtIndex(index);
    }

    public synchronized void reload() {
        this.browser.reload();
    }

    public synchronized void reloadIgnoringCache() {
        this.browser.reloadIgnoringCache();
    }

    public synchronized void reload(boolean checkForRepost) {
        this.browser.reload(checkForRepost);
    }

    public synchronized void reloadIgnoringCache(boolean checkForRepost) {
        this.browser.reloadIgnoringCache(checkForRepost);
    }

    public WebStorage getLocalWebStorage() {
        return this.browser.getLocalWebStorage();
    }

    public synchronized WebStorage getLocalWebStorage(long frameId) {
        return this.browser.getLocalWebStorage(frameId);
    }

    public WebStorage getSessionWebStorage() {
        return this.browser.getSessionWebStorage();
    }

    public synchronized WebStorage getSessionWebStorage(long frameId) {
        return this.browser.getSessionWebStorage(frameId);
    }

    public void replaceMisspelledWord(String word) {
        this.browser.replaceMisspelledWord(word);
    }

    public boolean addWordToSpellCheckerDictionary(String word) {
        return this.browser.addWordToSpellCheckerDictionary(word);
    }

    public void setAudioMuted(boolean muted) {
        this.browser.setAudioMuted(muted);
    }

    public boolean isAudioMuted() {
        return this.browser.isAudioMuted();
    }

    public boolean isAudioPlaying() {
        return this.browser.isAudioPlaying();
    }

    public void forwardKeyEvent(BrowserKeyEvent event) {
        this.browser.forwardKeyEvent(event);
    }

    public void forwardMouseEvent(BrowserMouseEvent event) {
        this.browser.forwardMouseEvent(event);
    }

    /**
     * 获取浏览器渲染进程相关信息
     *
     * @return RenderProcessInfo
     */
    public RenderProcessInfo getRenderProcessInfo() {
        return this.browser.getRenderProcessInfo();
    }

    public String getCustomStyleSheet() {
        return this.browser.getCustomStyleSheet();
    }

    public void setCustomStyleSheet(String customStyleSheet) {
        this.browser.setCustomStyleSheet(customStyleSheet);
    }

    /**
     * Load web page and wait until web page is loaded completely
     * <p>
     * 加载网页并等待网页完全加载, 在执行注册的回调函数
     *
     * @param browser 浏览器实例
     * @param task
     */
    public static void invokeAndWaitFinishLoadingMainFrame(JxBrowser browser, Callback<Browser> task) {
        Browser.invokeAndWaitFinishLoadingMainFrame(browser.browser, task);
    }

    public static void invokeAndWaitFinishLoadingMainFrame(JxBrowser browser, Callback<Browser> task, int timeoutInSeconds) {
        Browser.invokeAndWaitFinishLoadingMainFrame(browser.browser, task, timeoutInSeconds);
    }
}
