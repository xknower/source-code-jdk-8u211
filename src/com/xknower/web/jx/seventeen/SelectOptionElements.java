package com.xknower.web.jx.seventeen;


import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMOptionElement;
import com.teamdev.jxbrowser.chromium.dom.DOMSelectElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * DOMSelectElement class is used. Let's see how to access SELECT element on web page that contains the following HTML code:
 *
 * <select id='select-tag'>
 *   <option value="volvo">Volvo</option>
 *   <option value="saab">Saab</option>
 *   <option value="mercedes">Mercedes</option>
 *   <option value="audi">Audi</option>
 * </select>
 * To access SELECT element use the following JxBrowser API:
 *
 * Java
 * DOMDocument document = browser.getDocument();
 * DOMSelectElement select = (DOMSelectE<span class="fr-marker" data-id="0" data-type="false" style="display: none; line-height: 0;"></span><span class="fr-marker" data-id="0" data-type="true" style="display: none; line-height: 0;"></span>lement) document.findElement(By.id("select-tag"));
 * Now, using the select instance you can get information about its options, programmatically select specified option. For example:
 *
 * List<DOMOptionElement> options = select.getOptions();
 * options.get(2).setSelected(true);
 * The complete sample you can find below:
 */

/**
 * The sample demonstrates how to programatically select an option item in SELECT tag.
 */
public class SelectOptionElements {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    DOMDocument document = browser.getDocument();
                    DOMSelectElement select = (DOMSelectElement) document.findElement(By.id("select-tag"));
                    selectOptionByIndex(select, 2);
                }
            }
        });
        browser.loadHTML("<html><body><select id='select-tag'>\n" +
                "  <option value=\"volvo\">Volvo</option>\n" +
                "  <option value=\"saab\">Saab</option>\n" +
                "  <option value=\"opel\">Opel</option>\n" +
                "  <option value=\"audi\">Audi</option>\n" +
                "</select></body></html>");
    }

    private static void selectOptionByIndex(DOMSelectElement select, int index) {
        List<DOMOptionElement> options = select.getOptions();
        options.get(2).setSelected(true);
    }
}
