package com.xknower.web.jx.one;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The example demonstrates how to integrate UI built with HTML+CSS+JavaScript
 * into Java desktop application.
 * <p>
 * Desktop App UI built with HTML+CSS+JavaScript
 *
 * <p>
 * With JxBrowser your Java desktop app UI can bebuilt with HTML+CSS+JavaScript.
 * It means that you can actually use any modern HTML5 UI toolkit to build modern,
 * user-friendly interface of your Java desktop application.
 * You don’t need to hire Swing/AWT developers.
 * GUI of your Java app can be built by HTML+CSS+JavaScript developers.
 * It significantly reduces the cost of Java project development.
 * <p>
 * The following simple application demonstrates how to create New Account Dialog built with HTML+CSS+JavaScript
 * into your Java Swing/AWT desktop application.
 * <p>
 * <p>
 * This dialog has First Name, Last Name, Phone, Email text fields, and New Account button.
 * In Java application we need to display a dialog with this HTML content,
 * let the user to fill all text fields and click New Account button.
 * In Java code we need to be notified when user clicks the button,
 * read text fields values to create a new account in our application.
 * The following Java example demonstrates how we can do it with JxBrowser:
 */
public class HTMLUISample {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("HTMLUISample");
        final JButton newAccountButton = new JButton("New Account...");
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = createAccount(frame);
                // Displays created account's details
                JOptionPane.showMessageDialog(frame, "Created Account: " + account);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.add(newAccountButton);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(contentPane, BorderLayout.CENTER);
        frame.setSize(300, 75);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Account createAccount(JFrame parent) {
        final AtomicReference<Account> result = new AtomicReference<Account>();
        final JDialog dialog = new JDialog(parent, "New Account", true);

        // Create Browser instance.
        final Browser browser = new Browser();
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    JSValue value = browser.executeJavaScriptAndReturnValue("window");
                    value.asObject().setProperty("AccountService",
                            new AccountService(dialog, result));
                }
            }
        });
        // Load HTML with dialog's HTML+CSS+JavaScript UI.
        browser.loadURL("D://code//multi-thread//src//com//xknower//web//jx//one//dialog.html");

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Dispose Browser instance because we don't need it anymore.
                browser.dispose();
                // Close New Account dialog.
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Embed Browser Swing component into the dialog.
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setSize(400, 500);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result.get();
    }

    public static class AccountService {

        private final JDialog dialog;
        private final AtomicReference<Account> result;

        public AccountService(JDialog dialog, AtomicReference<Account> result) {
            this.dialog = dialog;
            this.result = result;
        }

        public void createAccount(String firstName, String lastName, String phone, String email) {
            result.set(new Account(firstName, lastName, phone, email));
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog.setVisible(false);
                }
            });
        }
    }

    public static class Account {
        public final String firstName;
        public final String lastName;
        public final String phone;
        public final String email;

        public Account(String firstName, String lastName, String phone, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.email = email;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
