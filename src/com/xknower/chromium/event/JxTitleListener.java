package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.events.BrowserListener;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;

import java.util.EventListener;

/**
 * @author xknower
 */
public class JxTitleListener implements TitleListener, BrowserListener, EventListener {

    /**
     * @param titleEvent [TitleEvent -> BrowserEvent]
     */
    @Override
    public void onTitleChange(TitleEvent titleEvent) {
    }
}
