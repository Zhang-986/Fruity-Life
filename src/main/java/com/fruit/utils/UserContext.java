package com.fruit.utils;

public class UserContext {

    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();

    /**
     * 设置当前用户的ID
     *
     * @param userId 用户ID
     */
    public static void setUserId(Long userId) {
        userIdHolder.set(userId);
    }

    /**
     * 获取当前用户的ID
     *
     * @return 用户ID，如果未设置则返回null
     */
    public static Long getUserId() {
        return userIdHolder.get();
    }

    /**
     * 清除当前用户的ID
     * <p>
     * 通常在请求处理完成后调用，以避免内存泄漏。
     * </p>
     */
    public static void clear() {
        userIdHolder.remove();
    }
}