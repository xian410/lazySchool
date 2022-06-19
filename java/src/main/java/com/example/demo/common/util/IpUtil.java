package com.example.demo.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ip工具
 * @author zuozhiwei
 */
public class IpUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取请求的ip
     * @param request   请求对象
     * @return          ip
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
