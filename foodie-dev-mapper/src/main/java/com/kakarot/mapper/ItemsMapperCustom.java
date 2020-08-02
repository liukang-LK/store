package com.kakarot.mapper;

import com.kakarot.pojo.vo.ItemCommentVO;
import com.kakarot.pojo.vo.searchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);

    public List<searchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);

    public List<searchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);

}
