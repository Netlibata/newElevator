package com.example.V1.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");  // 允许所有域名访问，生产环境中应设置具体的前端域名http://10.181.173.166:5173
        corsConfiguration.addAllowedHeader("*");  // 允许所有请求头
        corsConfiguration.addAllowedMethod("*");  // 允许所有HTTP方法
        corsConfiguration.setAllowCredentials(true);  // 允许发送cookie
        corsConfiguration.setMaxAge(3600L);  // 预检请求有效期1小时
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

}