package com.xknower.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Buff 工具类
 *
 * @Author 2018-12-18
 */
public final class ToolBuff {

    /**
     * 字节序数组, 转换为二进制位字符串
     */
    public static String byteArrToBinStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            byte temp = b[i];
            String str = Integer.toBinaryString((temp & 0xFF) + 0x100).substring(1);
            result.append(str);
        }
        return result.toString();
    }


    /**
     * 十六进制字符串, 转化成 ASCII码字符串
     *
     * @param hex 十六进制字符串
     * @return
     */
    public static String hexToAscii(String hex) {
        String result = "";
        int len = hex.length() / 2;
        for (int i = 0; i < len; i++) {
            int tmp = Integer.valueOf(hex.substring(2 * i, 2 * i + 2), 16).intValue();
            result = result + (char) tmp;
        }
        return result;
    }

    /**
     * int 转化成十六进制字符串 => 0x0000
     *
     * @param data
     * @return
     */
    public static String toHexString(int data) {
        String tmp = Integer.toHexString(data);
        StringBuilder sb = new StringBuilder("00000000");
        sb.replace(8 - tmp.length(), 8, tmp);
        return sb.toString().toUpperCase();
    }

    /**
     * int 转化成十六进制字符串 => 0x0000
     *
     * @param data
     * @param byteNum
     * @return
     */
    public static String toHexString(long data, int byteNum) {
        int len = byteNum * 2;
        String tmp = Long.toHexString(data);
        while (tmp.length() < len) {
            tmp = "0" + tmp;
        }
        int start = tmp.length() - len;
        tmp = tmp.substring(start);
        return tmp.toUpperCase();
    }

    // 数据直接获取解析

    public static byte getByte(int start, byte[] bytes) {
        return (byte) toUInt(start, 1, bytes);
    }

    /**
     * 字节数组转成无符合short类型
     *
     * @param start
     * @param bytes
     * @return
     */
    public static short getUnsignedShort(int start, byte[] bytes) {
        return (short) toUInt(start, 2, bytes);
    }

    /**
     * @param start
     * @param bytes
     * @return
     */
    public static int getInt(int start, byte[] bytes) {
        return toUInt(start, 4, bytes);
    }

    /**
     * @param start   定位指针
     * @param lenByte 获取字节数 [1B\2B\4B]
     * @param bytes   字节数组
     * @return
     */
    private static int toUInt(int start, int lenByte, byte[] bytes) {
        int value = 0;
        int m = start + lenByte;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    /**
     * 整数转字节数组
     *
     * @param num
     * @return
     */
    public static byte[] encodeToBytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >>> 24);
        result[1] = (byte) (num >>> 16);
        result[2] = (byte) (num >>> 8);
        result[3] = (byte) (num);
        return result;
    }

    /**
     * 字节数组转成无符合short类型
     *
     * @param bytes
     * @param start
     * @return
     */
    public static short decodeToShort(byte[] bytes, int start) {
        int value = 0;
        int m = start + 2;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return (short) value;
    }

    public static int decodeToInt(byte[] bytes, int start) {
        int value = 0;
        int m = start + 4;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static int ToUInt32(byte b) {
        int value = 0;
        int m = 4;
        int shift = (m - 1 - 3) * 8;
        value += (b & 0x000000FF) << shift;
        return value;
    }

    public static String format(Date date) {

        SimpleDateFormat time = new SimpleDateFormat("yy-MM-dd HH:mm:s");
        return time.format(date);
    }

    /**
     * 十六进制字符串, 转化成字节数组
     */
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;

        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);

            b[i] = ((byte) (parse(c0) << 4 | parse(c1)));
            //int start = i * 2;
            //int c = Integer.parseInt(hexstr.substring(start, start + 2));
            //b[i] = (byte)c;
        }

        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return c - 'a' + 10 & 0xF;
        }

        if (c >= 'A') {
            return c - 'A' + 10 & 0xF;
        }

        return c - '0' & 0xF;
    }

    public static String ToHexString(long data, int byteNum) {
        int totalLen = byteNum * 2;
        String tmp = Long.toHexString(data);
        while (tmp.length() < totalLen) {
            tmp = "0" + tmp;
        }
        int start = tmp.length() - totalLen;
        tmp = tmp.substring(start);
        return tmp;
    }

//    /**
//     * 字节序转化为十六进制字符串（大写）
//     */
//    public static String encodeHexString(byte[] bytes) {
//        return Hex.encodeHexString(bytes).toUpperCase();
//    }

    /**
     * 十六进制字符串转化为字节序数组
     */
    public static byte[] decodeHexString(String hexStr) {
        if (hexStr == null || "".equals(hexStr)) {
            return null;
        }
        hexStr = hexStr.toUpperCase();
        int length = hexStr.length() / 2;
        char[] hexChars = hexStr.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String decodeBCDHexStr(byte[] bytes) {
        return decodeBCDHexStr(bytes, true);
    }

    /**
     * BCD 编码 [字节数组, 十进制字符串]
     * BCD数组转十进制字符串
     *
     * @param bytes
     * @param flag  [是否首位补零 - true 补零]
     */
    private static String decodeBCDHexStr(byte[] bytes, boolean flag) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }

        // 去掉首位零encodeBcdTenStr
        String result = temp.toString();
        while (!flag && "0".equals(result.substring(0, 1))) {
            result = result.substring(1);
        }

        // 是否补零
        while (flag && result.length() < bytes.length) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * 日期转6位BCD码 [yyMMddHHmmss]
     *
     * @param date 日志时间
     * @return 字节流
     */
    public static byte[] encodeBCDTime(Date date) {
        int start = 0;
        byte[] bytes = new byte[6];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        bytes[start++] = Byte.parseByte("" + (year - 2000), 16);
        bytes[start++] = Byte.parseByte((month + ""), 16);
        bytes[start++] = Byte.parseByte((day + ""), 16);
        bytes[start++] = Byte.parseByte((hour + ""), 16);
        bytes[start++] = Byte.parseByte((minute + ""), 16);
        bytes[start++] = Byte.parseByte((second + ""), 16);
        return bytes;
    }

    /**
     * BCD时间字节数据转化成时间对象 [BCD 十进制]
     *
     * @param date 6B BCD时间 [GMT+8 YY-MM-DD-hh-mm-ss]
     * @return 日期时间
     */
    public static Date decodeBCDTime(byte[] date) {
        SimpleDateFormat f = new SimpleDateFormat("yyMMddHHmmss");
        String hexTime = decodeBCDHexStr(date);
        try {
            return f.parse(hexTime);
        } catch (Exception e) {
            return null;
        }
    }

//    /**
//     * 6位BCD码转成日期
//     *
//     * @param bytes
//     * @param start
//     * @return
//     */
//    public static Date decodeBCDTime(byte[] bytes, int start) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("20").append(String.format("%02X", bytes[start + 0]))
//                .append("-").append(String.format("%02X", bytes[start + 1]))
//                .append("-").append(String.format("%02X", bytes[start + 2]))
//                .append(" ").append(String.format("%02X", bytes[start + 3]))
//                .append(":").append(String.format("%02X", bytes[start + 4]))
//                .append(":").append(String.format("%02X", bytes[start + 5]));
//        String strDate = sb.toString();
//        Date d = null;
//        try {
//            d = DateUtils.parseDate(strDate, "yyyy-MM-dd HH:mm:ss");
//        } catch (ParseException e) {
////            e.printStackTrace();
//        }
//        return d;
//    }

    /**
     * 六位字节数据, 转化成时间
     *
     * @param date [GMT YY-MM-DD-hh-mm-ss]
     * @return
     */
    public static Date decodeHexBCDTime(byte[] date) {
        try {
            StringBuffer sb = new StringBuffer();
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            for (byte b : date) {
                // 每位存储进制 ["%02X" - 十六进制存储、"%02d"  - 十进制存储]
                sb.append(String.format("%02d", b));
            }
            return sdf.parse(sb.toString());
        } catch (ParseException pe) {
//            log.error(String.format("BCD 解析消息错误 => %s ", encodeHexString(date)), pe);
        }
        return null;
    }

    /**
     * 字符串流化 [GBK]
     *
     * @param str 字符串
     * @return 字节流
     */
    public static byte[] encodeGBKStr(String str) {
        try {
            return str.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
//            log.error(String.format("GBK流化字符串出错 =>  [ %s ]", str), e);
        }
        return null;
    }

    public static String decodeToGBKStr(byte[] bytes) {
        return decodeToStr(bytes, 0, bytes.length, "GBK");
    }

    public static String decodeToGBKStr(byte[] bytes, int start, int len) {
        return decodeToStr(bytes, start, len, "GBK");
    }

    public static String decodeToStr(byte[] bytes, int start, int len, String charsetName) {
        try {
            return new String(bytes, start, len, charsetName);
        } catch (UnsupportedEncodingException e) {
//            log.error(String.format("GBK转码后字符串出错 => [ %s ]", encodeHexString(bytes)), e);
            return "";
        }
    }

}
