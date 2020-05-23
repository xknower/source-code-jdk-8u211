package com.xknower.web.jx.one;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMFormControlElement;

/**
 * 自动化测试
 * <p>
 * JxBrowser API provides a lot of features that can be used for writing automated tests. You can:
 * <p>
 * Load URL, HTML string, local file and wait until web page is loaded completely.
 * Access JavaScript Console to read all messages.
 * Access JavaScript on the loaded web page and execute any JavaScript code.
 * Access DOM of the loaded web page. Read and modify DOM HTML elements.
 * Access and modify Cookies.
 * Access and modify HTTP request/response headers on the fly.
 * <br>
 * Using remote debugging port (--remote-debugging-port=9222) you can attach to JxBrowser from Google Chrome
 * to debug JavaScript on the loaded in JxBrowser web page.
 * <br>
 * Get notifications from web browser control about title, status, navigation, etc. events.
 * <br>
 * Handle JavaScript dialogs such as alert, confirmation, prompt. Configure Proxy settings per Browser instance and more.
 * <p>
 * For example, let’s test Facebook Login form and validate the use case when user enters invalid login and password.
 * Expected behavior is that Login form must display an error message:
 */
public class LoginFacebookTest {
    public static void main(String[] args) {
        final Browser browser = new Browser();

        // Load https://www.facebook.com/login.php and wait until web page is loaded completely.
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://www.facebook.com/login.php");
            }
        });
        // Access DOM document of the loaded web page.
        final DOMDocument document = browser.getDocument();
        // Find and enter email.
        ((DOMFormControlElement) document.findElement(By.id("email"))).setValue("user@mail.com");
        // Find and enter password.
        ((DOMFormControlElement) document.findElement(By.id("pass"))).setValue("123");
        // Find and click Login button and wait until a new web page is loaded completely.
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                document.findElement(By.id("u_0_2")).click();
            }
        });

        // Find Login form and get its inner HTML.
        String loginFormHTML = browser.getDocument().findElement(By.id("login_form")).getTextContent();
        // Check whether inner HTML has the required error message.
        boolean hasErrorMessage = loginFormHTML.contains("Please re-enter your password");
        if (!hasErrorMessage) {
            System.err.println("Test Failed: No error message is displayed.");
        }
        System.out.println("hasErrorMessage = " + hasErrorMessage);
    }
}
