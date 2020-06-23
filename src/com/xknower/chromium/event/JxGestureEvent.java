package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.events.GestureDevice;
import com.teamdev.jxbrowser.chromium.events.GestureEvent;
import com.teamdev.jxbrowser.chromium.events.GestureType;

/**
 * @author xknower
 */
public final class JxGestureEvent {

    private GestureEvent gestureEvent;

    public JxGestureEvent(int x, int y, int globalX, int globalY, GestureType type, GestureDevice sourceDevice, boolean isAltDown, boolean isAltGraphDown, boolean isShiftDown, boolean isControlDown, boolean isMetaDown) {
        this.gestureEvent = new GestureEvent(x, y, globalX, globalY, type, sourceDevice, isAltDown, isAltGraphDown, isShiftDown, isControlDown, isMetaDown);
    }

    public GestureEvent gestureEvent() {
        return this.gestureEvent;
    }

    //

    public final int getX() {
        return this.gestureEvent.getX();
    }

    public final int getY() {
        return this.gestureEvent.getY();
    }

    public final int getGlobalX() {
        return this.gestureEvent.getGlobalX();
    }

    public final int getGlobalY() {
        return this.gestureEvent.getGlobalY();
    }

    public final GestureType getType() {
        return this.gestureEvent.getType();
    }

    public final GestureDevice getSourceDevice() {
        return this.gestureEvent.getSourceDevice();
    }

    public final boolean isAltDown() {
        return this.gestureEvent.isAltDown();
    }

    public final boolean isAltGraphDown() {
        return this.gestureEvent.isAltGraphDown();
    }

    public final boolean isShiftDown() {
        return this.gestureEvent.isShiftDown();
    }

    public final boolean isControlDown() {
        return this.gestureEvent.isControlDown();
    }

    public final boolean isMetaDown() {
        return this.gestureEvent.isMetaDown();
    }
}
