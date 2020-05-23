package com.xknower.web.jx.five;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;

/**
 * Any web page can display pop-up windows using the window.open() JavaScript function. For example:
 * <p>
 * window.open("http://www.google.com", "_blank", "toolbar=yes, scrollbars=yes, resizable=yes, top=500, left=500, width=400, height=400");
 * <p>
 * Note: by default, pop-up windows are enabled and displayed in both Swing and JavaFX UI toolkits.
 * <p>
 * JxBrowser API provides the PopupHandler class to handle pop-up windows creation.
 * To handle pop-up windows creation you must register your own implementation of PopupHandler depending
 * on GUI toolkit you use in your Java application.
 * To get more information about how to handle pop-ups in Swing and JavaFX toolkits take a look at the following articles:
 * <p>
 * Disabling Popups
 * To disable pop-up windows you must create and register your own implementation of PopupHandler and return null from
 * the handlePopup() method as shown below:
 */
public class AboutPopups {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.setPopupHandler(new PopupHandler() {
            @Override
            public PopupContainer handlePopup(PopupParams params) {
                return null;
            }
        });
    }
}
