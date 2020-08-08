package com.kakarot.mapper;

import com.kakarot.my.mapper.MyMapper;
import com.kakarot.pojo.ItemsComments;

import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String,Object> map);

}
