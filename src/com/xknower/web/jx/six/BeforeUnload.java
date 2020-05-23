package com.xknower.web.jx.six;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.UnloadDialogParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;

import javax.swing.*;
import java.awt.*;

/**
 * The onbeforeunload event fires when the document is about to be unloaded.
 * This event allows you to display a message in a confirmation dialog box to inform the user whether he/she wants to stay or leave the current page.
 * JxBrowser API allows handling this dialog using DialogHandler API.
 * By default the dialog is displayed. Using your custom implementation of the DialogHandler,
 * you can handle this dialog in your own way. For example, you can display your custom message dialog or suppress
 * the dialog and don't allow unloading web page.
 * <p>
 * The following sample demonstrates how to handle the onbeforeunload dialog:
 * <p>
 * The sample demonstrates how to catch onbeforeunload dialog.
 */
public class BeforeUnload {
    public static void main(String[] args) {
        Browser browser = new Browser();
        final BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setVisible(true);

        browser.setDialogHandler(new DefaultDialogHandler(view) {
            @Override
            public CloseStatus onBeforeUnload(UnloadDialogParams params) {
                String title = "Confirm Navigation";
                String message = params.getMessage();
                int returnValue = JOptionPane.showConfirmDialog(view, message, title, JOptionPane.OK_CANCEL_OPTION);
                if (returnValue == JOptionPane.OK_OPTION) {
                    return CloseStatus.OK;
                } else {
                    return CloseStatus.CANCEL;
                }
            }
        });
        browser.loadHTML("<html><body onbeforeunload='return myFunction()'>" +
                "<a href='http://www.google.com'>Click here to leave</a>" +
                "<script>function myFunction() { return 'Leave this web page?'; }" +
                "</script></body></html>");
    }
}
