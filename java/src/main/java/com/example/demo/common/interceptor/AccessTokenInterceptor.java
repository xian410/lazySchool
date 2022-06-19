package com.example.demo.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.ApiResponse;
import com.example.demo.common.ErrorEnum;
import com.example.demo.common.annotation.AccessToken;
import com.example.demo.common.annotation.IgnoreAccessToken;
import com.example.demo.common.exception.CustomException;
import com.example.demo.common.util.IpUtil;
import com.example.demo.common.util.JsonUtil;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.mapper.LoginUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 拦截器
 * 用于日志打印、权限验证
 * @author 作者
 */
@Slf4j
public class AccessTokenInterceptor implements HandlerInterceptor {

    /**
     * 本线程存储计时器
     */
    private static final ThreadLocal<StopWatch> TIME = new ThreadLocal<>();

    /**
     * 本线程存储日志信息
     */
    private static final ThreadLocal<StringBuilder> LOG_INFO = new ThreadLocal<>();

    @Resource
    LoginUserMapper loginUserMapper;

    /**
     * 在请求之前需要处理的内容
     * @param request   请求对象
     * @param response  返回对象
     * @param handler   handler对象
     * @return          true or false
     * @throws Exception    异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 开始计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 计时器放在本线程中
        TIME.set(stopWatch);

        // 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取请求头部
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>(16);
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                headerMap.put(key, request.getHeader(key));
            }
        }
        // 生成请求id
        String requestId = String.valueOf(UUID.randomUUID()).replaceAll("-","");
        // 请求id放在request的域中
        request.setAttribute("requestId", requestId);
        // 请求id放在log上下文中
        MDC.put("requestId", requestId);
        // 准备日志信息
        StringBuilder sb = new StringBuilder();
        sb.append("\n【request_id】:").append(requestId);
        sb.append("\n【请求 URL】:").append(request.getRequestURL());
        sb.append("\n【请求 IP】:").append(IpUtil.getIp(request));
        sb.append("\n【请求Header】:").append(JsonUtil.toJson(headerMap));
        sb.append("\n【请求参数】:").append(JsonUtil.toJson(parameterMap));
        // 日志信息存放在本线程中
        LOG_INFO.set(sb);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        if (hm == null) {
            return true;
        }
        Class<?> clazz = hm.getBeanType();
        boolean isClzAnnotation = clazz.isAnnotationPresent(AccessToken.class);
        if (isClzAnnotation) {
            // 处理忽略token的情况，如果有token补充用户信息
            Method method = hm.getMethod();
            if (method != null) {
                IgnoreAccessToken ignoreAccessToken = method.getAnnotation(IgnoreAccessToken.class);
                if (ignoreAccessToken != null) {
                    return true;
                }
            }
            return checkToken(request, response);
        }

        Method method = hm.getMethod();
        if (method == null) {
            return true;
        }

        AccessToken accessToken = method.getAnnotation(AccessToken.class);
        if (accessToken == null) {
            return true;
        }
        return checkToken(request, response);
    }

    /**
     * 在请求之后
     * @param request       请求
     * @param response      响应
     * @param handler       handler
     * @param modelAndView  视图
     * @throws Exception    异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在请求全部完成之后
     * @param request   请求
     * @param response  响应
     * @param handler   handler
     * @param ex        异常
     * @throws Exception    异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 取出计时器
        StopWatch stopWatch = TIME.get();
        // 计时器停止
        stopWatch.stop();
        // 取计时器耗时
        long time = stopWatch.getTotalTimeMillis();
        // 计时器线程清除
        TIME.remove();

        // 取出日志信息
        StringBuilder localLog = LOG_INFO.get();
        // 日志信息线程清除
        LOG_INFO.remove();
        // 日志拼接接口耗时
        localLog.append("\n【接口耗时】:").append(time).append("ms");

        // 从request取aop打印的log信息
        StringBuilder logInfo = (StringBuilder) request.getAttribute("log");
        localLog.append(logInfo);
        // 打印请求日志
        log.info(localLog.toString());
        // 清除log上下文信息
        MDC.clear();
    }


    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            validateToken(request);
            return true;
        } catch (Exception e) {
            log.error("token验证失败，错误信息",e);
            String error = JsonUtil.toJson(ApiResponse.error(ErrorEnum.INVALID_ACCESS));

            PrintWriter writer = null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            writer = response.getWriter();
            writer.print(error);
            return false;
        }
    }
    /**
     * 验证token
     *
     * @param request
     * @return
     */
    private boolean validateToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token == null || "".equals(token)){
            token = request.getParameter("token");
        }
        if (Objects.isNull(token) || "".equals(token)) {
            log.warn("validateToken token为空");
            throw new CustomException(ErrorEnum.INVALID_ACCESS);
        }

        LoginUser loginUser = loginUserMapper.selectOne(
                Wrappers.<LoginUser>lambdaQuery().eq(LoginUser::getToken, token)
        );

        if (Objects.isNull(loginUser)) {
            log.warn("validateToken token验证失败 token:{}", token);
            throw new CustomException(ErrorEnum.INVALID_ACCESS);
        }

        // 域中存储用户信息
        request.setAttribute("loginUser", loginUser);
        log.info("validateToken 登录用的信息 token:{} userInfo:{}", token, JsonUtil.toJson(loginUser));
        return true;
    }
}
