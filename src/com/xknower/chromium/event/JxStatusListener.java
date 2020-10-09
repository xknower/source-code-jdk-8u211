package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.events.BrowserListener;
import com.teamdev.jxbrowser.chromium.events.StatusEvent;
import com.teamdev.jxbrowser.chromium.events.StatusListener;

import java.util.EventListener;

/**
 * @author xknower
 */
public class JxStatusListener implements StatusListener, BrowserListener, EventListener {

    /**
     * @param statusEvent [StatusEvent -> BrowserEvent]
     */
    @Override
    public void onStatusChange(StatusEvent statusEvent) {
    }
}
