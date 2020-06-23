package com.xknower.chromium;

import com.teamdev.jxbrowser.chromium.events.GestureEvent;

import com.xknower.chromium.event.JxInputEventsHandler;
import com.xknower.chromium.event.JxKeyAdapter;
import com.xknower.chromium.event.JxKeyEventType;
import com.xknower.chromium.event.JxMouseAdapter;
import com.xknower.utils.CallBack;
import javafx.scene.input.ScrollEvent;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 浏览器视图, 用于图形界面展示 (图形界面事件处理 : 键盘事件、鼠标事件)
 * <p>
 * 对 BrowserView (swing 、javafx) 的封装
 *
 * @author xknower
 */
public class JxBrowserView {

    private JxBrowser jxBrowser;

    // BrowserView -> JComponent -> Container -> Component -> ImageObserver, MenuContainer

    private com.teamdev.jxbrowser.chromium.swing.BrowserView sBrowserView;

    // BrowserView -> StackPane -> Pane -> Region -> Parent -> Node -> EventTarget, Styleable

    private com.teamdev.jxbrowser.chromium.javafx.BrowserView jBrowserView;

    public JxBrowserView(JxBrowser jxBrowser) {
        this.jxBrowser = jxBrowser;
    }

    public static JxBrowserView instance(JxBrowser jxBrowser) {
        return new JxBrowserView(jxBrowser);
    }

    public JxBrowser jxBrowser() {
        return this.jxBrowser;
    }

    public com.teamdev.jxbrowser.chromium.swing.BrowserView browserViewSwing() {
        if (this.sBrowserView == null) {
            synchronized (this) {
                if (this.sBrowserView == null) {
                    this.sBrowserView = new com.teamdev.jxbrowser.chromium.swing.BrowserView(this.jxBrowser.browser());
                }
            }
        }
        return this.sBrowserView;
    }

    public com.teamdev.jxbrowser.chromium.javafx.BrowserView browserViewFx() {
        if (this.jBrowserView == null) {
            synchronized (this) {
                if (this.jBrowserView == null) {
                    this.jBrowserView = new com.teamdev.jxbrowser.chromium.javafx.BrowserView(this.jxBrowser.browser());
                }
            }
        }
        return jBrowserView;
    }

    // swing

    /**
     * 注册键盘输入监听器处理器
     *
     * @param handler JxInputEventsHandler
     */
    public void setKeyEventsHandler(CallBack<KeyEvent, Boolean> handler) {
        this.browserViewSwing().setKeyEventsHandler(new JxInputEventsHandler<KeyEvent>() {
            @Override
            public boolean handle(KeyEvent event) {
//            String eventDesc = String.format(" 键盘事件: | %s %s %s %s | 事件编号 ID : %s, 事件源 Source : %s, Modifiers: %s, 时间 When : %s,ModifiersEx : %s | Component %s, paramString:  %s ",
//                    event.getKeyCode(), event.getExtendedKeyCode(), event.getKeyChar(), event.getKeyLocation(),
//                    event.getID(), ((JComponent) event.getSource()).getLocation(), event.getModifiers(), event.getWhen(), event.getModifiersEx(),
//                    ((JComponent) event.getComponent()).getLocation(), event.paramString()
//            );
//            System.out.println(eventDesc);
//            // event.isControlDown() && event.getKeyCode() == KeyEvent.VK_A
                if (handler != null) {
                    Boolean flag = handler.call(event);
                    return flag != null && flag;
                }
                return false;
            }
        });
    }

    /**
     * 注册鼠标输入监听器处理器
     *
     * @param handler JxInputEventsHandler
     */
    public void setMouseEventsHandler(CallBack<MouseEvent, Boolean> handler) {
        this.browserViewSwing().setMouseEventsHandler(new JxInputEventsHandler<MouseEvent>() {
            @Override
            public boolean handle(MouseEvent event) {
//            String eventDesc = String.format(" 鼠标事件: [ID: %s] | Point: %s, X: %s, Y: %s, ClickCount: %s |\n" +
//                            " XOnScreen: %s, YOnScreen: %s, Button: %s, LocationOnScreen: %s, ModifiersEx: %s ",
//                    event.getID(), event.getPoint(), event.getX(), event.getY(), event.getClickCount(),
//                    event.getXOnScreen(), event.getYOnScreen(),
//                    event.getButton(), event.getLocationOnScreen(), event.getModifiersEx()
//            );
//            if (event.getID() != MouseEvent.MOUSE_MOVED) {
//                System.out.println(eventDesc);
//            }
                if (handler != null) {
                    Boolean flag = handler.call(event);
                    return flag != null && flag;
                }
                return false;
            }
        });
    }

    /**
     * 注册触屏(手势)输入监听器处理器
     *
     * @param handler JxInputEventsHandler
     */
    public void setGestureEventsHandler(JxInputEventsHandler<GestureEvent> handler) {
        this.browserViewSwing().setGestureEventsHandler(handler);
    }

    public void setDragAndDropEnabled(boolean enabled) {
        this.browserViewSwing().setDragAndDropEnabled(enabled);
    }

    public boolean isDragAndDropEnabled() {
        return this.browserViewSwing().isDragAndDropEnabled();
    }

    public void requestFocus() {
        this.browserViewSwing().requestFocus();
    }

    public boolean requestFocus(boolean temporary) {
        return this.browserViewSwing().requestFocus(temporary);
    }

    public boolean requestFocusInWindow() {
        return this.browserViewSwing().requestFocusInWindow();
    }

    /**
     * 页面渲染开关
     *
     * @param visible true 开启页面渲染, false 关闭页面渲染
     */
    public void setVisible(boolean visible) {
        if (sBrowserView != null && !jxBrowser().isDisposed()) {
            sBrowserView.setVisible(visible);
        }
        if (jBrowserView != null && !jxBrowser().isDisposed()) {
            jBrowserView.setVisible(visible);
        }
    }

    public void forwardKeyEvent(KeyEvent event, JxKeyEventType eventType) {
        this.browserViewSwing().forwardKeyEvent(event, eventType.keyEventType());
    }

    // javafx

    public void setKeyEventsHandlerFx(JxInputEventsHandler<javafx.scene.input.KeyEvent> handler) {
        this.browserViewFx().setKeyEventsHandler(handler);
    }

    public void setMouseEventsHandlerFx(JxInputEventsHandler<javafx.scene.input.MouseEvent> handler) {
        this.browserViewFx().setMouseEventsHandler(handler);
    }

    public void setGestureEventsHandlerFx(JxInputEventsHandler<GestureEvent> handler) {
        this.browserViewFx().setGestureEventsHandler(handler);
    }

    public void setScrollEventsHandlerFx(JxInputEventsHandler<ScrollEvent> handler) {
        this.browserViewFx().setScrollEventsHandler(handler);
    }

    public void setDragAndDropEnabledFx(boolean enabled) {
        this.browserViewFx().setDragAndDropEnabled(enabled);
    }

    public boolean isDragAndDropEnabledFx() {
        return this.browserViewFx().isDragAndDropEnabled();
    }

    public void requestFocusFx() {
        this.browserViewFx().requestFocus();
    }

    public void forwardKeyEvent(javafx.scene.input.KeyEvent event) {
        this.browserViewFx().forwardKeyEvent(event);
    }

    // swing ->

    public void addSwingKeyListener(JxKeyAdapter keyAdapter) {
        // (组件)
        // Keyboard Events 键盘时间
        // You can listen to Keyboard and Mouse events in the BrowserView Swing control using the following way Keyboard Events.
        // 您可以使用以下键盘事件方式收听BrowserView Swing控件中的键盘和鼠标事件
        // The sample demonstrates how to register KeyListener to Browser component.
        browserViewSwing().addKeyListener(keyAdapter);
    }

    public void addSwingMouseListener(JxMouseAdapter keyAdapter) {
        // The sample demonstrates how to register MouseListener to Browser component.
        browserViewSwing().addMouseListener(keyAdapter);
    }
}
