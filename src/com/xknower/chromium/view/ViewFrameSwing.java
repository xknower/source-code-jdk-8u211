package com.xknower.chromium.view;

import com.xknower.utils.CallBack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

/**
 * 显示界面容器 [swing]
 *
 * @author xknower
 */
public class ViewFrameSwing {

    private final JFrame frame;

    public ViewFrameSwing(JComponent component) {
        this("", component);
    }

    public ViewFrameSwing(String title, JComponent component) {
        frame = new JFrame(title);
        //
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //
        frame.add(component, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(false);
    }

    /**
     * 窗口事件处理器
     *
     * @param windowAdapter
     */
    public void addWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * 窗口渲染开关
     *
     * @param visible true 开启窗口界面渲染, false 关闭窗口界面渲染
     */
    public void setVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> {
            //
            if (frame != null) {
                frame.setVisible(visible);
            }
        });
    }

    /**
     * 开启界面
     *
     * @param callback 界面回调函数
     */
    public void viewShow(CallBack<ViewFrameSwing, WindowAdapter> callback) {
        SwingUtilities.invokeLater(() -> {
            //
            if (callback != null) {
                WindowAdapter windowAdapter = callback.call(this);
                if (windowAdapter != null) {
                    this.addWindowListener(windowAdapter);
                }
            }
        });
    }
}
