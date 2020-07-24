package com.kakarot.enums;

/**
 * @author Joker
 * @Desc: 性别 枚举
 */
public enum  Sex {

    /**
     * 0代表性别女，1代表性别男，2代表性别保密
     */
    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    /**
     * public字段，所以可以直接访问，不需要用getType()等方法
     */
    public final Integer type;
    public final String value;

    Sex(Integer type,String value){
        this.type = type;
        this.value = value;
    }

}
