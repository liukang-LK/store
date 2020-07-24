package com.kakarot.service.impl;

import com.kakarot.mapper.TestStuMapper;
import com.kakarot.pojo.TestStu;
import com.kakarot.service.TestStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestStuServiceImpl implements TestStuService {

    @Autowired
    private TestStuMapper testStuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TestStu getStuInfo(int id) {
        return testStuMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {
        TestStu stu = new TestStu();
        stu.setName("uzi");
        stu.setAge(23);
        testStuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        testStuMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {
        TestStu stu = new TestStu();
        stu.setId(id);
        stu.setName("faker");
        stu.setAge(18);
        testStuMapper.updateByPrimaryKey(stu);
    }

    @Override
    public void saveParent() {
        TestStu stu = new TestStu();
        stu.setName("parent");
        stu.setAge(40);
        testStuMapper.insert(stu);
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveChildren() {
        saveChild1();
        //int a = 1 / 0;
        saveChild2();
    }

    @Override
    public void saveChild1() {
        TestStu stu = new TestStu();
        stu.setName("child-1");
        stu.setAge(9);
        testStuMapper.insert(stu);
    }

    @Override
    public void saveChild2() {
        TestStu stu = new TestStu();
        stu.setName("child-2");
        stu.setAge(8);
        testStuMapper.insert(stu);
    }


}
