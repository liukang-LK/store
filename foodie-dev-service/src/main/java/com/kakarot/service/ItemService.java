package com.kakarot.service;

import com.kakarot.pojo.Items;
import com.kakarot.pojo.ItemsImg;
import com.kakarot.pojo.ItemsParam;
import com.kakarot.pojo.ItemsSpec;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品ID查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品ID查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

}
