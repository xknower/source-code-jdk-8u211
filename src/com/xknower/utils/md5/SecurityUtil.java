package com.xknower.utils.md5;

import com.xknower.utils.HexUtils;

import java.math.BigInteger;
import java.util.Random;

/**
 * 密码安全帮助类
 *
 * @author xknower
 */
public final class SecurityUtil {

    private static final BigInteger PRIVATE_D = new BigInteger(
            "3206586642942415709865087389521403230384599658161226562177807849299468150139");

    private static final BigInteger N = new BigInteger(
            "7318321375709168120463791861978437703461807315898125152257493378072925281977");

    private SecurityUtil() {

    }

    /**
     * 解析前台传送的加密字符
     *
     * @param str
     * @return
     * @see
     */
    public static String decryptPassword(String str) {
        byte ptext[] = HexUtils.toByteArray(str);
        BigInteger encryC = new BigInteger(ptext);

        BigInteger variable = encryC.modPow(PRIVATE_D, N);
        // 计算明文对应的字符串
        byte[] mt = variable.toByteArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = mt.length - 1; i > -1; i--) {
            buffer.append((char) mt[i]);
        }
        return buffer.substring(0, buffer.length() - 10).toString();
    }

    /**
     * 生成密码安全码
     */
    public static String getNewPsw() {
        String s1 = MD5Util.md5Hex(String.valueOf(System.currentTimeMillis()));
        String s2 = UUIDUtil.getUUID();
        return s1 + s2;
    }

    /**
     * 生成加密后的密码
     */
    public static String getStoreLogpwd(String usercode, String logpwd, String psw) {
        return MD5Util.md5Hex(usercode + MD5Util.md5Hex(logpwd) + psw);
    }

    public static String getVehicleSharePwd(String psw, String uuid) {
        return MD5Util.md5Hex(uuid + psw);
    }

    /**
     * 生成随机的MD5密文
     */
    public static String getNewToken() {
        return MD5Util.md5Hex(java.util.UUID.randomUUID().toString());
    }

    /**
     * 生成随机正整数
     */
    public static int getRandomInt(int maxInt) {
        Random random = new Random();
        return random.nextInt(maxInt);
    }

    /**
     * 生成签名 [MD5 签名]
     */
    public static String getDigest(String srcStr) {
        return MD5Util.md5Hex(MD5Util.md5Hex(srcStr) + (srcStr.hashCode() + srcStr.length()) + "19880322");
    }

    /**
     * 生成指定长度的验证码
     */
    public static String getVerifyCode(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(getRandomInt(10));
        }
        return sb.toString();
    }
}
