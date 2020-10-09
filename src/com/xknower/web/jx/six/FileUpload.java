package com.xknower.web.jx.six;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.FileChooserMode;
import com.teamdev.jxbrowser.chromium.FileChooserParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;

import java.awt.BorderLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Using JxBrowser DialogHandler API you can handle situation when web page needs to display File Open dialog
 * (e.g. when user clicks File Input element on a web form).
 * Using this API you can display your own File Open dialog or suppress the dialog at all and provide path to a file program.
 * <p>
 * Note: By default JxBrowser displays standard Java Swing/JavaFX File Open dialog.
 * <p>
 * The following sample demonstrates how to display your File Open dialog using Java Swing API:
 * <p>
 * The sample demonstrates how to register your DialogHandler and
 * override the functionality that displays file chooser when
 * user uploads file using INPUT TYPE="file" HTML element on a web page.
 */
public class FileUpload {
    public static void main(String[] args) {
        Browser browser = new Browser();
        final BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setDialogHandler(new DefaultDialogHandler(view) {
            @Override
            public CloseStatus onFileChooser(final FileChooserParams params) {
                final AtomicReference<CloseStatus> result = new AtomicReference<CloseStatus>(
                        CloseStatus.CANCEL);

                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            if (params.getMode() == FileChooserMode.Open) {
                                JFileChooser fileChooser = new JFileChooser();
                                if (fileChooser.showOpenDialog(view)
                                        == JFileChooser.APPROVE_OPTION) {
                                    File selectedFile = fileChooser.getSelectedFile();
                                    params.setSelectedFiles(selectedFile.getAbsolutePath());
                                    result.set(CloseStatus.OK);
                                }
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                return result.get();
            }
        });
        browser.loadURL("http://www.cs.tut.fi/~jkorpela/forms/file.html");
    }
}
