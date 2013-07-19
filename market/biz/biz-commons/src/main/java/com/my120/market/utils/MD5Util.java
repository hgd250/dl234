package com.my120.market.utils;

import com.my120.commons.util.DigestUtil;

/**
 * MD5工具类
 * 
 * @author cai.yc
 * 
 *         2012-11-28
 */
public class MD5Util {

    /**
     * MD5散列
     * 
     * @author cai.yc 2012-11-23
     * @param src
     * @return 32位十六进制大写字符串
     */
    public static String encrypt(String src) {
        if (src == null || src.equals("")) {
            return "";
        }
        DigestUtil digest = DigestUtil.getMD5Digest();
        byte pwddata[] = digest.createDigest(src.getBytes());
        return bytes2hex(pwddata);
    }

    /**
     * 字节数组转换成16进制(转换成大写)
     * 
     * @param bytes
     *            字节数组
     * @return 字节数组的16进制字符串
     */
    public static String bytes2hex(byte[] bytes) {
        return bytes2hex2Natural(bytes).toUpperCase();

    }

    /**
     * 字节数组转换成16进制(未转换大小写)
     * 
     * @param bytes
     *            字节数组
     * @return 字节数组的16进制字符串
     */
    private static String bytes2hex2Natural(byte[] bytes) {
        int len = bytes.length;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            String temp = Integer.toHexString(bytes[i]);
            switch (temp.length()) {
                case 0:
                case 1:
                    temp = "0" + temp;
                    break;
                default:
                    temp = temp.substring(temp.length() - 2);
                    break;
            }
            sb.append(temp);
        }
        return sb.toString();

    }
}
