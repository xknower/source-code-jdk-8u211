package com.xknower.utils.md5;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
public class IdGen {

    private static SecureRandom random = new SecureRandom();

    /**
     * YYYYMMDDHHMMSS+6位自增长码(20位)
     */
    private static int sequence = 0;
    private static int length = 6;

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static synchronized String getLocalTrmSeqNum() {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String s = Integer.toString(sequence);
        return datetime + addLeftZero(s, length);
    }

    /**
     * 获取批次号.
     */
    public static synchronized String getSeqBatchNo(String prefix) {
        String datetime = new SimpleDateFormat("yyyyMMddSSSHHmmss").format(new Date());
        return prefix + datetime;
    }

    /**
     * 左填0
     */
    public static String addLeftZero(String s, int length) {
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException("Numeric value is larger than intended length: " + s + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);

    }
}
