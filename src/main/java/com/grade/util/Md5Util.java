package com.grade.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类（用于密码加密存储）
 */
public class Md5Util {

    /**
     * 对字符串进行 MD5 加密
     */
    public static String encrypt(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    /**
     * 校验明文密码是否与密文匹配
     */
    public static boolean verify(String plain, String cipher) {
        if (plain == null || cipher == null) {
            return false;
        }
        return encrypt(plain).equals(cipher);
    }
}
