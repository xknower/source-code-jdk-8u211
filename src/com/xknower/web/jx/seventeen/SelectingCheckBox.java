package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMInputElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * To programmatically select/unselect HTML input type=checkbox element use the DOMInputElement.setChecked(boolean checked) method.
 * The following sample demonstrates how to find checkbox element on loaded web page and select it:
 * <p>
 * The sample demonstrates how to programatically select checkbox.
 */
public class SelectingCheckBox {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMInputElement element = (DOMInputElement) document.findElement(By.name("agreement"));
                    element.setChecked(true);
                }
            }
        });
        browser.loadHTML("<html><body><form action=''>" +
                "<input type='checkbox' name='agreement' value='agreed'>I agree<br>" +
                "</form></body></html>");
    }
}
