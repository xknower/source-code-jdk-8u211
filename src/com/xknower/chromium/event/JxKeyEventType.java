package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.BrowserKeyEvent.KeyEventType;

/**
 * 键盘事件类型
 *
 * @author xknower
 */
public enum JxKeyEventType {

    // 按下
    PRESSED(401),
    // 松开
    RELEASED(402),
    //
    TYPED(400);

    private final int value;

    JxKeyEventType(int value) {
        this.value = value;
    }

    public static JxKeyEventType Value(int value) {
        for (JxKeyEventType v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return null;
    }

    public int Value() {
        return this.value;
    }

    public KeyEventType keyEventType() {
        JxKeyEventType v = JxKeyEventType.Value(this.value);
        switch (v) {
            case PRESSED:
                return KeyEventType.PRESSED;
            case RELEASED:
                return KeyEventType.RELEASED;
            case TYPED:
                return KeyEventType.TYPED;
            default:
                throw new IllegalArgumentException("Unsupported value: " + value);
        }
    }
}
