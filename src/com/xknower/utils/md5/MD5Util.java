package com.xknower.utils.md5;

import com.xknower.utils.HexUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Static functions to simplifiy common {@link MessageDigest} tasks. This class is thread safe.
 * <p>
 * 线程安全的 MD5 工具类
 *
 * @author xknower
 */
final class MD5Util {

    private static final BigInteger PRIVATE_D = new BigInteger(
            "3206586642942415709865087389521403230384599658161226562177807849299468150139");
    private static final BigInteger N = new BigInteger(
            "7318321375709168120463791861978437703461807315898125152257493378072925281977");

    private MD5Util() {
    }

    /**
     * Returns a MessageDigest for the given <code>algorithm</code>.
     * <p>
     * The MessageDigest algorithm name.
     *
     * @return An MD5 digest instance.
     * @throws RuntimeException when a {@link NoSuchAlgorithmException} is
     *                          caught
     */
    private static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element. 计算MD5摘要, 十六位元素返回.
     * <code>byte[]</code>.
     *
     * @param data Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element.
     * <code>byte[]</code>.
     *
     * @param data    Data to digest. 待计算摘要的字符串数据.
     * @param charset 数据字符串编码格式
     * @return MD5 digest.
     */
    public static byte[] md5(String data, String charset) {
        if (charset == null) {
            return md5(data.getBytes());
        } else {
            try {
                return md5(data.getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                //
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string. 计算摘要并返回32位摘要字符串.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(byte[] data) {
        return HexUtils.toHexString(md5(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(String data) {
        return md5Hex(data, null);
    }

    /**
     * 指定编码加密
     *
     * @param data
     * @param charset
     * @return
     * @author lihe 2013-7-4 下午5:27:07
     * @see
     * @since
     */
    public static String md5Hex(String data, String charset) {
        return HexUtils.toHexString(md5(data, charset));
    }
}