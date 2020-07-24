package com.kakarot.service.impl;

import com.kakarot.service.TestStuService;
import com.kakarot.service.TestTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestTransServiceImpl implements TestTransService {

    @Autowired
    private TestStuService testStuService;


    /**
     * 事务传播 - Propagation  -主要使用 REQUIRED、SUPPORTS（查询）
     * REQUIRED ：使用当前的事务，如果当前没有事务，则自己新建一个事务，子方法是必须运行在一个事务中
     *            如果当前存在事务，则加入这个事务，成为一个整体。
     *            举例：领导没饭吃，我有钱，我会自己买了自己吃；领导有的吃，会分给你一起吃。
     * SUPPORTS ：如果当前有事务，则使用事务；如果当前没有事务，则不使用事务。
     *            举例：领导没饭吃，我也没饭吃；领导有饭吃，我也有饭吃。
     * MANDATORY ：（强制的）该传播属性强制必须存在一个事务，如果不存在，则抛出异常。
     *             举例：领导必须管饭，不管饭没饭吃，我就不干了（抛异常）
     * REQUIRES_NEW ：如果当前有事务，则挂起该事务，并且自己创建一个新的事务给自己使用；
     *                如果当前没有事务，则同REQUIRED
     *                举例：领导有饭吃，我偏不要，我自己买了自己吃
     * NOT_SUPPORTED ：如果当前有事务，则把事务挂起，自己不使用事务去运行数据库操作
     *                 举例：领导有饭吃，分一点给你，我偏不吃
     * NEVER ：如果当前有事务存在，则抛出异常
     *         举例：领导有饭给你吃，我不想吃去工作（抛出异常）
     * NESTED ：（嵌套的）如果当前有事务，则开启子事务（嵌套事务），嵌套事务是独立提交或者回滚；
     *          如果当前没有事务，则同REQUIRED。
     *          但是如果主事务提交，则会携带子事务一起提交。
     *          如果主事务回滚，则子事务会一起回滚。相反，子事务异常，则父事务可以回滚或不会滚。
     *          举例：领导决策不对，老板怪罪，领导带着小弟一同受罪。小弟除了差错，领导可以推卸责任。
     */

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testPropagationTrans() {

        testStuService.saveParent();

        testStuService.saveChildren();

    }
}
