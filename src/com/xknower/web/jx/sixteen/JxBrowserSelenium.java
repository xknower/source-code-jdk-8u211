package com.xknower.web.jx.sixteen;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriverService;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

/**
 * JxBrowser is compatible with Selenium WebDriver. To use Selenium with JxBrowser perform the following steps:
 * <p>
 * 1. Download ChromeDriver and extract it somewhere.
 */
public class JxBrowserSelenium {

}

// 2. Run JxBrowser with the --remote-debugging-port switcher as shown in the example below:

/**
 * This sample demonstrates how to create Browser instance and provide access to the Chromium remote debugging server.
 */
class BrowserSample {
    public static void main(String[] args) {
        // When Chromium process starts with this switcher, Chromium remote debugging server will be available
        // at http://localhost:9222
        BrowserPreferences.setChromiumSwitches("--remote-debugging-port=9222");

        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.google.com");
    }
}

// 3. Create Selenium WebDriver and use the remote debugging port (9222) from the previous step to connect to JxBrowser.

/**
 * This class demonstrates how to create Selenium WebDriver and connect to the Chromium remote debugging server
 */
class SeleniumSample {
    public static void main(String[] args) throws IOException {
//        // Create ChromeDriverService that allows you to run ChromeDriver and start it.
//        ChromeDriverService service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File("path/to/my/chromedriver"))
//                .usingAnyFreePort()
//                .build();
//        service.start();
//
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        ChromeOptions options = new ChromeOptions();
//
//        // The address string should be in the form of "hostname/ip:port". 9222 is the port that you specified in the
//        // --remote-debugging-port Chromium switcher before starting JxBrowser.
//        String remoteDebuggingAddress = "localhost:9222";
//
//        // Set the address to the appropriate ChromeOption option and apply it to the DesiredCapabilities.
//        // The full list of these options you can find at
//        // https://sites.google.com/a/chromium.org/chromedriver/capabilities
//        options.setExperimentalOption("debuggerAddress", remoteDebuggingAddress);
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//
//        // Create a WebDriver instance using URL provided by the ChromeDriverService and capabilities with
//        // JxBrowser remote debugging address.
//        WebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);
//
//        // Now you can use WebDriver.
//        System.out.println(driver.getCurrentUrl());
//
//        // Quit from the current session to prevent Chromium remote debugging server hang.
//        driver.quit();
//        service.stop();
    }
}

// This approach was tested with Selenium WebDriver 2.46 and ChromeDriver 2.16.