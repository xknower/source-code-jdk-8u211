package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.events.BrowserListener;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;

import java.util.EventListener;

/**
 * @author xknower
 */
public class JxConsoleListener implements ConsoleListener, BrowserListener, EventListener {

    /**
     * @param consoleEvent [ConsoleEvent -> BrowserEvent]
     */
    @Override
    public void onMessage(ConsoleEvent consoleEvent) {
    }
}
