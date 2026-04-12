package com.hengtiansoft.fastop.base.common.context;

/**
 * 基于 ThreadLocal 的用户上下文持有器。
 * 由拦截器在请求进入时写入，请求结束时清除。
 */
public class UserContextHolder {

    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(String username) {
        currentUser.set(username);
    }

    public static String getCurrentUser() {
        String user = currentUser.get();
        return user != null ? user : "unknown";
    }

    public static void clear() {
        currentUser.remove();
    }
}
