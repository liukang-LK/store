package com.kakarot.enums;

/**
 * @Desc: 是否 枚举
 */
public enum YesOrNo {

    /**
     * 0否，1是
     */
    NO(0,"否"),
    YES(1,"是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type,String value){
        this.type=type;
        this.value=value;
    }

}
