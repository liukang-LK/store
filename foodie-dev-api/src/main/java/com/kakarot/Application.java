package com.kakarot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * MapperScan注解   扫描mybatis通用mapper所在的包
 * ComponentScan注解   扫描所有包以及相关组件包（不写则默认扫描此类所在的包）
 * springboot 会自动扫描入口类App所在的包，即com.kakarot
 */
@MapperScan(basePackages = "com.kakarot.mapper")
@ComponentScan(basePackages = {"com.kakarot","org.n3r.idworker"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
