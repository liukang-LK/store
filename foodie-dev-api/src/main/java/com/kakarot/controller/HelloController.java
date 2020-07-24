package com.kakarot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LK
 * RestController注解，返回出去的是json对象
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello(){
        return "Hello World~";
    }

}
