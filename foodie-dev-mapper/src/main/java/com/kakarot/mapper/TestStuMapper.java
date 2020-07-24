package com.kakarot.mapper;

import com.kakarot.my.mapper.MyMapper;
import com.kakarot.pojo.TestStu;
import org.springframework.stereotype.Repository;

/**
 * 已经在Application用MapperScan注解进行包扫描了，所以不用加Repository注解
 * 这里加上是为了处理idea编辑器的bug，不加会有红线
 */
@Repository
public interface TestStuMapper extends MyMapper<TestStu> {
}