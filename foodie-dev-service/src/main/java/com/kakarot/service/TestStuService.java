package com.kakarot.service;

import com.kakarot.pojo.TestStu;
import org.springframework.stereotype.Service;


public interface TestStuService {

    //测试
    public TestStu getStuInfo(int id);
    public void saveStu();
    public void deleteStu(int id);
    public void updateStu(int id);

    //理解事务
    public void saveParent();
    public void saveChildren();
    public void saveChild1();
    public void saveChild2();

}
