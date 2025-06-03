package com.fruit.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordUtils {

    /**
     * 加密密码
     *
     * @param password 原始密码
     * @return 加密后的字符串
     */
    public static String encryptPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解密密码
     *
     * @param encryptedPassword 加密后的密码
     * @return 原始密码字符串
     */
    public static String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            return null;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

}
