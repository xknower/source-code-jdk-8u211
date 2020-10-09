package com.xknower.chromium.net;

import com.teamdev.jxbrowser.chromium.ResourceType;

/**
 * 网络资源类型定义
 *
 * @author xknower
 */
public enum JxResourceType {
    //
    MAIN_FRAME(0),
    //
    SUB_FRAME(1),
    //
    STYLESHEET(2),
    //
    SCRIPT(3),
    // 图片资源
    IMAGE(4),
    //
    FONT_RESOURCE(5),
    //
    SUB_RESOURCE(6),
    //
    OBJECT(7),
    //
    MEDIA(8),
    //
    WORKER(9),
    //
    SHARED_WORKER(10),
    //
    PREFETCH(11),
    //
    FAVICON(12),
    //
    XHR(13),
    //
    PING(14),
    //
    SERVICE_WORKER(15),
    //
    RESOURCE_TYPE_CSP_REPORT(16),
    //
    RESOURCE_TYPE_PLUGIN_RESOURCE(17),
    //
    LAST_TYPE(18);

    private final int value;

    JxResourceType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static JxResourceType value(int value) {
        for (JxResourceType v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unsupported value: " + value);
    }

    public static JxResourceType value(ResourceType resourceType) {
        switch (resourceType) {
            case MAIN_FRAME:
                return JxResourceType.MAIN_FRAME;
            case SUB_FRAME:
                return JxResourceType.SUB_FRAME;
            case STYLESHEET:
                return JxResourceType.STYLESHEET;
            case SCRIPT:
                return JxResourceType.SCRIPT;
            case IMAGE:
                return JxResourceType.IMAGE;
            case FONT_RESOURCE:
                return JxResourceType.FONT_RESOURCE;
            case SUB_RESOURCE:
                return JxResourceType.SUB_RESOURCE;
            case OBJECT:
                return JxResourceType.OBJECT;
            case MEDIA:
                return JxResourceType.MEDIA;
            case WORKER:
                return JxResourceType.WORKER;
            case SHARED_WORKER:
                return JxResourceType.SHARED_WORKER;
            case PREFETCH:
                return JxResourceType.PREFETCH;
            case FAVICON:
                return JxResourceType.FAVICON;
            case XHR:
                return JxResourceType.XHR;
            case PING:
                return JxResourceType.PING;
            case SERVICE_WORKER:
                return JxResourceType.SERVICE_WORKER;
            case RESOURCE_TYPE_CSP_REPORT:
                return JxResourceType.RESOURCE_TYPE_CSP_REPORT;
            case RESOURCE_TYPE_PLUGIN_RESOURCE:
                return JxResourceType.RESOURCE_TYPE_PLUGIN_RESOURCE;
            case LAST_TYPE:
                return JxResourceType.LAST_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported value: " + resourceType);
        }
    }

    public ResourceType resourceType() {
        JxResourceType v = JxResourceType.value(this.value);
        switch (v) {
            case MAIN_FRAME:
                return ResourceType.MAIN_FRAME;
            case SUB_FRAME:
                return ResourceType.SUB_FRAME;
            case STYLESHEET:
                return ResourceType.STYLESHEET;
            case SCRIPT:
                return ResourceType.SCRIPT;
            case IMAGE:
                return ResourceType.IMAGE;
            case FONT_RESOURCE:
                return ResourceType.FONT_RESOURCE;
            case SUB_RESOURCE:
                return ResourceType.SUB_RESOURCE;
            case OBJECT:
                return ResourceType.OBJECT;
            case MEDIA:
                return ResourceType.MEDIA;
            case WORKER:
                return ResourceType.WORKER;
            case SHARED_WORKER:
                return ResourceType.SHARED_WORKER;
            case PREFETCH:
                return ResourceType.PREFETCH;
            case FAVICON:
                return ResourceType.FAVICON;
            case XHR:
                return ResourceType.XHR;
            case PING:
                return ResourceType.PING;
            case SERVICE_WORKER:
                return ResourceType.SERVICE_WORKER;
            case RESOURCE_TYPE_CSP_REPORT:
                return ResourceType.RESOURCE_TYPE_CSP_REPORT;
            case RESOURCE_TYPE_PLUGIN_RESOURCE:
                return ResourceType.RESOURCE_TYPE_PLUGIN_RESOURCE;
            case LAST_TYPE:
                return ResourceType.LAST_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported value: " + value);
        }
    }
}
