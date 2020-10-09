package com.xknower.utils.md5;

/**
 * <p>
 * </p>
 *
 * @version 1.0
 */
public class UUIDUtil {
    private static UIDFactory uuid = null;

    static {
        try {
            uuid = UIDFactory.getInstance("UUID");
        } catch (Exception unsex) {
            unsex.printStackTrace();
        }
    }

    /**
     * Constructor for the UUIDGener object
     */
    private UUIDUtil() {
    }

    /**
     * 获取uuid字符
     *
     * @return
     * @author lihe 2013-7-4 下午5:31:09
     * @see
     * @since
     */
    public static String getUUID() {
        return uuid.getNextUID();
    }
}
