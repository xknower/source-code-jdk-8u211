package com.xknower.web.jx.six;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DownloadHandler;
import com.teamdev.jxbrowser.chromium.DownloadItem;
import com.teamdev.jxbrowser.chromium.events.DownloadEvent;
import com.teamdev.jxbrowser.chromium.events.DownloadListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * JxBrowser provides API that allows handling all the file downloads. Using this API you can control
 * what files should be downloaded, provide path to the destination directory where file must be saved and track download progress.
 * <p>
 * The DownloadItem class provides methods for pausing and canceling download process. See its corresponding methods.
 * <p>
 * The complete example that demonstrates how to register your own DownloadHandler implementation you can find below:
 * <p>
 * The sample demonstrates how to handle file download. To cancel download
 * you must return {@code false} from the
 * {@link DownloadHandler#allowDownload(com.teamdev.jxbrowser.chromium.DownloadItem)}
 * method. To listed for download update events you can register your own
 * {@link com.teamdev.jxbrowser.chromium.events.DownloadListener}.
 */
public class FileDownload {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // You can register your own implementation of the DownloadHandler interface to handle file downloads in your own way. The following sample demonstrates how to do this:
        browser.setDownloadHandler(new DownloadHandler() {
            @Override
            public boolean allowDownload(DownloadItem download) {
                download.addDownloadListener(new DownloadListener() {
                    @Override
                    public void onDownloadUpdated(DownloadEvent event) {
                        DownloadItem download = event.getDownloadItem();
                        if (download.isCompleted()) {
                            System.out.println("Download is completed!");
                        }
                    }
                });
                System.out.println("Destination file: " +
                        download.getDestinationFile().getAbsolutePath());
                return true;
            }
        });

        browser.loadURL("ftp://ftp.teamdev.com/updates/jxbrowser-4.0-beta.zip");
    }
}
