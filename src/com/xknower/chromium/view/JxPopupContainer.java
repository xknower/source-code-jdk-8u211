package com.xknower.chromium.view;

import com.teamdev.jxbrowser.chromium.PopupParams;
import com.xknower.chromium.JxBrowser;

import java.awt.*;

/**
 * 子窗口弹出
 *
 * @author xknower
 */
public class JxPopupContainer {

    final PopupParams popupParams;

    final JxBrowser jxBrowser;

    final Rectangle initialBounds;

    public JxPopupContainer(PopupParams popupParams, JxBrowser JxBrowser, Rectangle initialBounds) {
        this.popupParams = popupParams;
        this.jxBrowser = JxBrowser;
        this.initialBounds = initialBounds;
    }

    public PopupParams getPopupParams() {
        return popupParams;
    }

    public JxBrowser getJxBrowser() {
        return jxBrowser;
    }

    public Rectangle getInitialBounds() {
        return initialBounds;
    }
}
