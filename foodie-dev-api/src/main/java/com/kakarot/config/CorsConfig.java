package com.kakarot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域请求，对响应的设置，浏览器根据响应头部的一些信息才可以判断能否跨域请求到数据
 */

@Configuration
public class CorsConfig {

    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){

        //1、添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://shop.z.liukang.space:8080");
        config.addAllowedOrigin("http://center.z.liukang.space:8080");
        config.addAllowedOrigin("http://shop.z.liukang.space");
        config.addAllowedOrigin("http://center.z.liukang.space");
        config.addAllowedOrigin("*");

        // 设置是否发送cookie信息
        config.setAllowCredentials(true);

        // 设置允许请求的方式
        config.addAllowedMethod("*");

        // 设置允许的header
        config.addAllowedHeader("*");

        // 2. 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);

        // 3. 返回重新定义好的corsSource
        return new CorsFilter(corsSource);

    }

}
