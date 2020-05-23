package com.xknower.web.jx.six;

import com.teamdev.jxbrowser.chromium.*;

import javax.swing.*;

/**
 * JavaScript engine provides three types of dialogs: Alert, Confirm and Prompt.
 * When JavaScript engine needs to display one of these dialogs, an appropriate method of the DialogHandler is invoked:
 * <p>
 * 1. The DialogHandler.onAlert() method is invoked when JavaScript alert dialog should be displayed.
 * It happens when the window.alert() JavaScript function is invoked. The dialog displays text message and OK" button.
 * JavaScript execution will be blocked until Alert dialog is closed.
 * <p>
 * 2. The DialogHandler.onConfirmation() method is invoked when JavaScript confirmation dialog should be displayed.
 * It happens when the window.confirm() JavaScript function is invoked. The dialog displays text message,
 * "OK" and "Cancel" buttons. If the user clicks "OK", the box returns true. If the user clicks "Cancel",
 * the box returns false. JavaScript execution will be blocked until Confirm dialog is closed.
 * <p>
 * 3. The DialogHandler.onPrompt() method is invoked when JavaScript prompt dialog should be displayed.
 * It happens when the window.prompt() JavaScript function is invoked. The dialog displays a text input field
 * where user can enter some text, "OK" and "Cancel" buttons. To provide the new prompt text back to JavaScript
 * you can use the PromptDialogParams.setPromptText(String) method. If the user clicks "OK" the box returns the input value.
 * If the user clicks "Cancel" the box returns null. JavaScript execution will be blocked until Prompt dialog is closed.
 * <p>
 * By default JxBrowser doesn't display JavaScript dialogs and use silent mode where all dialogs are automatically
 * closed emulating behavior when user clicks Cancel button on the dialog.
 * <p>
 * To display JavaScript dialogs you can register your own implementation of the DialogHandler
 * which displays appropriate UI dialogs. For example, the following code demonstrates
 * how to register custom DialogHandler implementation that displays Alert dialog using Java Swing API
 * and cancels Confirm and Prompt dialogs:
 */
public class JavaScriptDialogs {

    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.setDialogHandler(new DialogHandler() {
            @Override
            public void onAlert(DialogParams params) {
                String url = params.getURL();
                String title = "The page at " + url + " says:";
                String message = params.getMessage();
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public CloseStatus onConfirmation(DialogParams params) {
                return CloseStatus.CANCEL;
            }

            @Override
            public CloseStatus onPrompt(PromptDialogParams params) {
                return CloseStatus.CANCEL;
            }

            @Override
            public CloseStatus onFileChooser(FileChooserParams fileChooserParams) {
                return null;
            }

            @Override
            public CloseStatus onBeforeUnload(UnloadDialogParams unloadDialogParams) {
                return null;
            }

            @Override
            public CloseStatus onSelectCertificate(CertificatesDialogParams certificatesDialogParams) {
                return null;
            }

            @Override
            public CloseStatus onReloadPostData(ReloadPostDataParams reloadPostDataParams) {
                return null;
            }

            @Override
            public CloseStatus onColorChooser(ColorChooserParams colorChooserParams) {
                return null;
            }
            //
        });

        // Or you can register JxBrowser's DialogHandler implementation that already implements and displays UI dialogs:

        // Swing
        //Browser browser = new Browser();
        //BrowserView browserView = new BrowserView(browser);
        //browser.setDialogHandler(new DefaultDialogHandler(browserView));

        // JavaFX
        //Browser browser = new Browser();
        //BrowserView browserView = new BrowserView(browser);
        //browser.setDialogHandler(new DefaultDialogHandler(browserView));
    }
}
