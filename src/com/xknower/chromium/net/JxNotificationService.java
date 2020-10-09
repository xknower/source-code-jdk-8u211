package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.NotificationHandler;
import com.teamdev.jxbrowser.chromium.NotificationService;
import com.xknower.chromium.JxBrowser;

/**
 * @author xknower
 */
public class JxNotificationService {

    private NotificationService notificationService;

    private JxNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 通知服务
     *
     * @param notificationService NotificationService
     * @return JxNotificationService
     */
    public static JxNotificationService instance(NotificationService notificationService) {
        return new JxNotificationService(notificationService);
    }

    public static JxNotificationService instance(JxBrowser jxBrowser) {
        return new JxNotificationService(jxBrowser.getContext().getNotificationService());
    }

    //

    public final void setNotificationHandler(JxNotificationHandler handler) {
        this.notificationService.setNotificationHandler(handler);
    }

    public final NotificationHandler getNotificationHandler() {
        return this.notificationService.getNotificationHandler();
    }
}
