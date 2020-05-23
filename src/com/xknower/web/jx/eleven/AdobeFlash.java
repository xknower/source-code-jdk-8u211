package com.xknower.web.jx.eleven;


import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser supports Adobe Flash plugin and can display Flash content on Windows and macOS platforms.
 * In order to display Flash contents you need to download and install Flash Player.
 * Flash content is displayed in windowless mode, so make sure that Flash content you display on the web page really supports this mode.
 * <p>
 * NPAPI & PPAPI Plugins
 * Since April 2015 Google Chrome stopped supporting NPAPI plugins.
 * JxBrowser 6.4 and higher doesn't support NPAPI plugins as well. We recommend that you download and install Flash PPAPI plugin.
 * <p>
 * Windows & Mac
 * To download Flash PPAPI plugin go to https://get.adobe.com/flashplayer/otherversions,
 * select your operating system and PPAPI plugin version in the second dropdown menu as shown blow:
 * <p>
 * JxBrowser will automatically find installed Flash PPAPI plugin and use it to display Flash content.
 * <p>
 * Console Window Issue
 * On Windows you may notice that the PPAPI Flash player plugin briefly displays and hides a console window during startup.
 * This console window displays the "NOT SANDBOXED" text and disappears immediately.
 * The reason of this behavior is in disabled Chromium sandbox. By default, the Chromium sandbox is disabled.
 * To get rid of this flashing console window you must enable Chromium sandbox.
 * <p>
 * Linux
 * Installation process of PPAPI Flash can be different depending on Linux distribution.
 * The following instruction describes how to install PPAPI Flash on Ubuntu 12.04+.
 * <p>
 * 1. Add PPA repository and install pepflashplugin-installer package.
 * // sudo add-apt-repository ppa:skunk/pepper-flash
 * // sudo apt-get update
 * // sudo apt-get install pepflashplugin-installer
 * <p>
 * 2. Specify path to the installed PPAPI Flash if necessary. JxBrowser searches PPAPI Flash in the default locations which
 * are different on various Linux distributions. Here is the list of the locations where JxBrowser expects to find PPAPI Flash:
 * <p>
 * // /usr/lib/pepflashplugin-installer/
 * // /usr/lib/pepperflashplugin-nonfree/
 * // /usr/lib/chromium/PepperFlash/
 * // /usr/lib64/chromium/PepperFlash/
 * <p>
 * Linux
 * Installation process of PPAPI Flash can be different depending on Linux distribution.
 * The following instruction describes how to install PPAPI Flash on Ubuntu 12.04+.
 * <p>
 * 1. Add PPA repository and install pepflashplugin-installer package.
 * <p>
 * // sudo add-apt-repository ppa:skunk/pepper-flash
 * // sudo apt-get update
 * // sudo apt-get install pepflashplugin-installer
 * <p>
 * 2. Specify path to the installed PPAPI Flash if necessary. JxBrowser searches PPAPI Flash in the default locations
 * which are different on various Linux distributions. Here is the list of the locations where JxBrowser expects to find PPAPI Flash:
 * <p>
 * /usr/lib/pepflashplugin-installer/
 * /usr/lib/pepperflashplugin-nonfree/
 * /usr/lib/chromium/PepperFlash/
 * /usr/lib64/chromium/PepperFlash/
 */

/**
 * Linux
 * Installation process of PPAPI Flash can be different depending on Linux distribution.
 * The following instruction describes how to install PPAPI Flash on Ubuntu 12.04+.
 * <p>
 * 1. Add PPA repository and install pepflashplugin-installer package.
 * <p>
 * sudo add-apt-repository ppa:skunk/pepper-flash
 * sudo apt-get update
 * sudo apt-get install pepflashplugin-installer
 * <p>
 * <p>
 * 2. Specify path to the installed PPAPI Flash if necessary. JxBrowser searches PPAPI Flash in the default locations
 * which are different on various Linux distributions. Here is the list of the locations where JxBrowser expects to find PPAPI Flash:
 * <p>
 * /usr/lib/pepflashplugin-installer/
 * /usr/lib/pepperflashplugin-nonfree/
 * /usr/lib/chromium/PepperFlash/
 * /usr/lib64/chromium/PepperFlash/
 */
// In case you have installed PPAPI Flash at a different location you must specify path
// to the Flash library using Chromium switchers as shown in the following sample:

/**
 * This sample loads a web page with simple Flash content.
 */
public class AdobeFlash {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches(
                "--ppapi-flash-path=/usr/lib/flash/libpepflashplayer.so",
                "--ppapi-flash-version=20.0.0.270");
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame("Flash Sample");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.webthrower.com/portfolio/narnia.htm");
    }
}
