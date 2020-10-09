package com.xknower.web.jx.seventeen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.io.File;
import javax.swing.*;
import java.awt.*;

/**
 * This example demonstrates how to use JavaScript MutationObserver in combination with JavaScript Java Bridge API.
 * The goal of this example is to transmit every change of the HTML element to the Java side and display it in the Output window.
 * <p>
 * The HTML page that we are listening to has a timer that updates the value of the span element.
 * The source code of the page is the following:
 */
// <!DOCTYPE html>
//<html>
//<head>
//  <title>index</title>
//</head>
//<body>
//<div>
//  <span class="counter" id="counter"></span>
//</div>
//<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
//        crossorigin="anonymous"></script>
//<script type="text/javascript">
//        $(document).ready(function () {
//          var counter = 1;
//          setInterval(function() { $(".counter").text(counter++); }, 1000);
//        });
//    </script>
//</body>
//</html>

/**
 * The source code of the Swing application that displays the web page, injects MutationObserver and listens to it is the following:
 */
public class ListeningToTheСontentСhanges {

    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent event) {
                Browser browser = event.getBrowser();
                JSObject window = browser.executeJavaScriptAndReturnValue("window")
                        .asObject();
                window.setProperty("java", new JavaObject());
                browser.executeJavaScript("window.onload = function() {"
                        + "const element = document.getElementById('counter');\n"
                        + "const observer = new MutationObserver(\n"
                        + "    function(mutations) {\n"
                        + "        window.java.onDomChanged(element.innerHTML);\n"
                        + "    });\n"
                        + "const config = {childList: true};\n"
                        + "observer.observe(element, config); };");
            }
        });

        String path = new File(".").getAbsolutePath();

        browser.loadURL(path + "/index.html");
    }

    /**
     * The object observing DOM changes.
     * <p>
     * <p>The class and methods that are invoked from JavaScript code must be public.
     */
    public static class JavaObject {

        @SuppressWarnings("unused") // invoked by callback processing code.
        public void onDomChanged(String innerHtml) {
            System.out.println("DOM node changed: " + innerHtml);
        }
    }
}
