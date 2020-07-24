package com.test;

import com.kakarot.Application;
import com.kakarot.service.TestTransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * RunWith : JUnit4提供的注解，指定使用的单元测试执行类，将Spring和JUnit连接起来
 * SpringBootTest ：替代了Spring-Test中的@ContextConfiguration注解，目的是加载ApplicationContext，启动Spring容器，
 *                  且SpringBootTest会自动检索配置文件，检索顺序是从当前包开始，逐级向上查找被@SpringBootApplication或@SpringBootConfiguration注解的类
 * 通过@RunWith和@SpringBootTest启动Spring容器，不需要自己去注册spring容器
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestTrans {

    @Autowired
    private TestTransService testTransService;

    @Test
    public void myTest(){
        testTransService.testPropagationTrans();
    }
}
