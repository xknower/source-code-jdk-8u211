package com.xknower.utils;

/**
 * This class provides convenient functions to convert hex string to byte array and vice versa.
 * <p>
 * 十六进制及字符串转换工具类
 *
 * @author xknower
 */
public final class HexUtils {

    private static final String HEX_CHARS = "0123456789abcdef";

    private HexUtils() {
    }

    /**
     * Converts a byte array to hex string. 字节数组转换成十六进制字符串.
     *
     * @param b -
     *          the input byte array
     * @return hex string representation of b.
     */
    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(HexUtils.HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
            sb.append(HexUtils.HEX_CHARS.charAt(b[i] & 0x0F));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Converts a hex string into a byte array. 十六进制字符串转换为字节数组.
     *
     * @param s -
     *          string to be converted
     * @return byte array converted from s
     */
    public static byte[] toByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(s.charAt(j++), 16) << 4) | Character
                    .digit(s.charAt(j++), 16));
        }
        return buf;
    }

}
