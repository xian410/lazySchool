package com.example.demo.common.config;

import com.example.demo.common.interceptor.AccessTokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义注册配置
 * @author 作者
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 要匹配的文件url路径
     */
    @Value("${file.pathPattern}")
    private String pathPattern;

//    /**
//     * 被匹配的文件磁盘路径
//     */
//    @Value("${file.location}")
//    private String location;

    /**
     * 注入拦截器
     * @return AccessTokenInterceptor
     */
    @Bean
    public AccessTokenInterceptor accessTokenInterceptor() {

        return new AccessTokenInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry  注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的token拦截器，然后配置拦截器的规则
        registry.addInterceptor(accessTokenInterceptor())
                // 添加此拦截器匹配的 所有请求路径
                .addPathPatterns("/**")
                // 此拦截器需要排除的请求路径
                .excludePathPatterns("/management/login")
                .excludePathPatterns("/management/register")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/error/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String pathProject = System.getProperty("user.dir");
        if (!pathProject.endsWith("\\java")) {
            pathProject += "\\java";
        }
        String location = pathProject + "/upload/";
        System.out.println(location);
        registry.addResourceHandler(pathPattern).addResourceLocations("file:" + location);
    }
}
