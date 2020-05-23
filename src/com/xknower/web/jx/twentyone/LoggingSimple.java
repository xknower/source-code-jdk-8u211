package com.xknower.web.jx.twentyone;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.*;

/**
 * Logging in JxBrowser is based on Java Logging, so you can configure JxBrowser logging using standard Java Logging API.
 * By default, all JxBrowser log messages are printed into console output. The default log level is Level.SEVERE.
 * <p>
 * You can change default log level using the "jxbrowser.logging.level" VM parameter. For example:
 * <p>
 * -Djxbrowser.logging.level=ALL
 * <p>
 * <p>
 * Or, you can change default log level using the com.teamdev.jxbrowser.chromium.LoggerProvider class:
 * <p>
 * HTML
 * LoggerProvider.setLevel(java.util.logging.Level.SEVERE);
 * <p>
 * Disabling Logging
 * To disable JxBrowser logging use the following code:
 * <p>
 * HTML
 * LoggerProvider.setLevel(java.util.logging.Level.OFF);
 * <p>
 * Redirecting Logging to File
 * If you want to redirect log message to a file, then you can use the following way:
 * <p>
 * Redirecting Logging to File
 * If you want to redirect log message to a file, then you can use the following way:
 * <p>
 * Redirecting Logging to File
 * If you want to redirect log message to a file, then you can use the following way:
 */

/**
 * Redirecting Logging to File
 * If you want to redirect log message to a file, then you can use the following way:
 */

/**
 * This sample demonstrates how to redirect all JxBrowser log
 * messages to the '*.log' files.
 */
public class LoggingSimple {
    public static void main(String[] args) throws IOException {
        LoggerProvider.setLevel(Level.ALL);

        // Redirect Browser log messages to jxbrowser-browser.log
        redirectLogMessagesToFile(LoggerProvider.getBrowserLogger(),
                "D:/jxbrowser-browser.log");

        // Redirect IPC log messages to jxbrowser-ipc.log
        redirectLogMessagesToFile(LoggerProvider.getIPCLogger(),
                "D:/jxbrowser-ipc.log");

        // Redirect Chromium Process log messages to jxbrowser-chromium.log
        redirectLogMessagesToFile(LoggerProvider.getChromiumProcessLogger(),
                "D:/jxbrowser-chromium.log");

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://google.com");
    }

    private static void redirectLogMessagesToFile(Logger logger, String logFilePath)
            throws IOException {
        FileHandler fileHandler = new FileHandler(logFilePath);
        fileHandler.setFormatter(new SimpleFormatter());

        // Remove default handlers including console handler
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        logger.addHandler(fileHandler);
    }
}

// Debugging on Mac
// JxBrowser runs all native code and Chromium engine in separate processes. When unexpected error happens in Chromium engine you might see unexpected process termination. To find out root cause of the error we need that you collect error info from generated crash dump files.
//
//When error happens in Chromium engine, the crash dump file with all necessary information about the error is automatically generated on Mac OS X. You can find generated crash dump files in Console app, User Diagnostic Reports log list. JxBrowser report has the jxbrowser_2015-04-05-150659_Vladimirs-MacBook-Pro.crash name.
//
//Copy content of this crash report and send it to us as attachment to your support ticket or share the generated core dump file with the JxBrowser Support team using an online file sharing service such as Dropbox, Google Drive, etc.
//
//Here's how crash report in Console app looks:


// Debugging on Windows
// Getting crash dump file
//By default, on Windows platforms, when Chromium engine is crashed, JxBrowser generates jxbrowser-chromium.dmp crash dump file and stores it in the %localappdata%\JxBrowser\ directory (e.g. c:/users\<username>\appdata\local\JxBrowser\jxbrowser-chromium.dmp).
//
//
//
//When you see that Chromium engine is unexpectedly terminated or crashed, please go to the %localappdata%\JxBrowser\ directory and share with us the jxbrowser-chromium.dmp file using an online file sharing service such as Dropbox, Google Drive, etc.
//
//
//Configuring crash dump folder
//To change directory where crash dump files will be generated to and stored, use the jxbrowser.dmp.dir System Property. For example:
//
//Java
//System.setProperty("jxbrowser.dmp.dir", "C:\\JxBrowser\\crash-dumps");
//
//
//Disabling crash dump generation
//To disable crash dump generation set the jxbrowser.dmp.dir System Property to an empty string:
//
//Java
//System.setProperty("jxbrowser.dmp.dir", "");
//
//
//
//
//Getting crash dump file using Microsoft Debug Diagnostic Tool
//To debug JxBrowser native binaries please use Microsoft Debug Diagnostic Tool to catch all exceptions in jxbrowser-chromium.exe processes.
//
//Here's instruction about how to configure this software to catch all native crashes/exceptions:
//
//1. Download Microsoft Debug Diagnostic Tool (both 32- and 64-bit versions).
//2. Install it in your target Windows environment. To install it just run MSI file.
//3. Run your application and create at least one Browser instance.
//4. Run Debug Diagnostic Tool and follow the instruction below:
//4.1. Click Add Rule... button:
// Now you can work with your Java application and try to reproduce native crash in jxbrowser-chromium.exe process. When native crash happens, this tool will generate DMP file in the directory you provided at 4.6 step.
//
//When you reproduce native crash and get DMP file/files, please report the issue and share the generated crash dump file using an online file sharing service such as Dropbox, Google Drive, etc.
//We will analyze DMP files, find out root cause of the native crash and fix it.


// Debugging on Linux
// By default, on Linux platforms nothing happens when the Chromium engine, used by JxBrowser, has been unexpectedly crashed. To collect information about the native crash and determine root cause of the issue, we need to get a core dump file. The core dump file is generated by operating system. By default, core dump file generation is disabled. To enable code dump file generation you need to configure your Linux environment using specific steps.
//
//
//
//The following short instruction explains how to enable core dump generation on Ubuntu:
//
//
//
//This instruction is valid for JxBrowser 6.16 and higher. With the previous JxBrowser versions, core dump file generation isn't supported.
//
//
//
//Configuring Ubuntu 14.04 and higher
//By default, Ubuntu doesn't generate crash dump files. To enable core dump file generation you must modify the /proc/sys/kernel/core_pattern file via the following command:
//
//$ echo '/tmp/core.%e.%p.%t' > /proc/sys/kernel/core_pattern
//It will change a path for the core dump files, so those files would have the name with the /tmp/core.exe_name.pid.time pattern.
//
//Also, you must change the quota for storing dump files with the next command:
//
//$ ulimit -c unlimited
//After reboot all the changes will be reverted to default. To prevent this you need to edit the /etc/sysctl.conf file and set the kernel.core_pattern parameter to the required value. For example, in the /etc/sysctl.conf file put/modify the following property:
//
//kernel.core_pattern = /tmp/core.%e.%p.%t
//To enable core dump files generation on the operating system startup you must add the following line to /etc/profile:
//
//ulimit -c unlimited
//When crash happens in the Chromium engine, the core dump file will be generated and saved in the /tmp directory with the core.%e.%p.%t name.
//
//
//
//Please share the generated core dump file with the JxBrowser Support team using an online file sharing service such as Dropbox, Google Drive, etc.