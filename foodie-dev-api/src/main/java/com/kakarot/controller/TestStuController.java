package com.kakarot.controller;

import com.kakarot.pojo.TestStu;
import com.kakarot.service.TestStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testStu")
public class TestStuController {

    @Autowired
    private TestStuService testStuService;

    @GetMapping("/getStuInfo")
    public TestStu getStuInfo(int id){
        return testStuService.getStuInfo(id);
    };

    @PostMapping("/saveStu")
    public Object saveStu(){
        testStuService.saveStu();
        return "OK";
    };

    @PostMapping("/deleteStu")
    public Object deleteStu(int id){
        testStuService.deleteStu(id);
        return "ok";
    };

    @PostMapping("/updateStu")
    public Object updateStu(int id){
        testStuService.updateStu(id);
        return "ok";
    };

}
