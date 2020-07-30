package com.kakarot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LK
 * RestController注解，返回出去的是json对象
 */
@RestController
public class HelloController {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello(){

        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");

        return "Hello World~";
    }

}
