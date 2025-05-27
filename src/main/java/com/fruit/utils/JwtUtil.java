package com.fruit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Slf4j
public class JwtUtil {

    // 强烈建议将密钥存储在配置文件或环境变量中，而不是硬编码
    // 生成一个安全的密钥，例如使用HS512算法
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // 令牌有效期（例如：1小时）
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1小时，单位毫秒
    // 令牌有效期（例如：7天，用于“记住我”功能）
    private static final long REMEMBER_ME_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7天

    /**
     * 从token中提取用户名
     *
     * @param token JWT令牌
     * @return 用户名
     */
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从token中提取过期时间
     *
     * @param token JWT令牌
     * @return 过期时间
     */
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从token中提取指定的Claim
     *
     * @param token          JWT令牌
     * @param claimsResolver Claim解析函数
     * @param <T>            Claim值的类型
     * @return Claim值
     */
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析token中的所有Claims
     *
     * @param token JWT令牌
     * @return Claims对象
     */
    private static Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("无法解析JWT令牌: {}", e.getMessage());
            return null; // 或者抛出自定义异常
        }
    }

    /**
     * 检查token是否过期
     *
     * @param token JWT令牌
     * @return 如果过期则返回true，否则返回false
     */
    private static Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    /**
     * 生成JWT令牌
     *
     * @param subject   通常是用户名或用户ID
     * @param rememberMe 是否“记住我”，如果是，则使用更长的有效期
     * @return 生成的JWT令牌
     */
    public static String generateToken(String subject, boolean rememberMe) {
        Map<String, Object> claims = new HashMap<>();
        // 你可以在这里添加额外的claims
        // claims.put("userId", userId);
        // claims.put("roles", userRoles);
        return createToken(claims, subject, rememberMe);
    }

    /**
     * 创建JWT令牌
     *
     * @param claims     要包含在令牌中的自定义声明
     * @param subject    令牌的主题（通常是用户名）
     * @param rememberMe 是否“记住我”
     * @return 生成的JWT令牌
     */
    private static String createToken(Map<String, Object> claims, String subject, boolean rememberMe) {
        long expirationTime = rememberMe ? REMEMBER_ME_EXPIRATION_TIME : EXPIRATION_TIME;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 验证令牌是否有效
     *
     * @param token   JWT令牌
     * @param subject 期望的令牌主题（例如用户名）
     * @return 如果令牌有效且与提供的用户名匹配，则返回true
     */
    public static Boolean validateToken(String token, String subject) {
        try {
            final String usernameInToken = extractUsername(token);
            return (usernameInToken != null && usernameInToken.equals(subject) && !isTokenExpired(token));
        } catch (Exception e) {
            log.warn("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 仅验证令牌是否有效（不检查subject）
     *
     * @param token JWT令牌
     * @return 如果令牌有效则返回true
     */
    public static Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.warn("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    // 可选：添加一个main方法用于测试
    public static void main(String[] args) {
        String username = "testUser";

        // 生成令牌
        String token = generateToken(username, false);
        System.out.println("Generated Token: " + token);

        // 模拟一段时间后
        try {
            Thread.sleep(1000); // 等待1秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 提取用户名
        String extractedUsername = extractUsername(token);
        System.out.println("Extracted Username: " + extractedUsername);

        // 验证令牌
        Boolean isValid = validateToken(token, username);
        System.out.println("Is token valid for " + username + "? " + isValid);

        Boolean isGenerallyValid = validateToken(token);
        System.out.println("Is token generally valid? " + isGenerallyValid);

        // 测试过期 (需要将EXPIRATION_TIME设置得很短，例如1毫秒，然后取消注释下面的代码)
        // String expiredToken = generateToken("expiredUser", false); // 假设EXPIRATION_TIME为1ms
        // try {
        //     Thread.sleep(10);
        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        // }
        // System.out.println("Is expired token valid? " + validateToken(expiredToken));
        // System.out.println("Username from expired token: " + extractUsername(expiredToken));
    }
}