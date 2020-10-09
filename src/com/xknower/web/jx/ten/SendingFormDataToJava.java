package com.xknower.web.jx.ten;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

/**
 * The sample demonstrates how to send form data from JavaScript to Java.
 */
public class SendingFormDataToJava {
    public static void main(String[] args) {
        LoggerProvider.setLevel(Level.OFF);

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    JSValue value = browser.executeJavaScriptAndReturnValue("window");
                    value.asObject().setProperty("Account", new Account());
                }
            }
        });

        browser.loadURL("form.html");
    }

    /**
     * Java class used for binding Java to JavaScript must be public.
     */
    public static class Account {
        public void save(String firstName, String lastName) {
            System.out.println("firstName = " + firstName);
            System.out.println("lastName = " + lastName);
        }
    }
}

//   Where the form.html file has the following content:
// <!DOCTYPE html>
//<html>
//<head>
//    <script type="text/javascript">
//          function sendFormData() {
//            var firstNameValue = myForm.elements['firstName'].value;
//            var lastNameValue = myForm.elements['lastName'].value;
//            // Invoke the 'save' method of the 'Account' Java object.
//            Account.save(firstNameValue, lastNameValue);
//          }
//    </script>
//</head>
//<body>
//<form name="myForm">
//    First name: <input type="text" name="firstName"/>
//    <br/>
//    Last name: <input type="text" name="lastName"/>
//    <br/>
//    <input type="button" value="Save" onclick="sendFormData();"/>
//</form>
//</body>
//</html>