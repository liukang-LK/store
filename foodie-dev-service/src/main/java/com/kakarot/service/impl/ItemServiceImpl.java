package com.kakarot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kakarot.enums.CommentLevel;
import com.kakarot.enums.YesOrNo;
import com.kakarot.mapper.*;
import com.kakarot.pojo.*;
import com.kakarot.pojo.vo.CommentLevelCountsVO;
import com.kakarot.pojo.vo.ItemCommentVO;
import com.kakarot.pojo.vo.ShopcartVO;
import com.kakarot.pojo.vo.searchItemsVO;
import com.kakarot.service.ItemService;
import com.kakarot.utils.DesensitizationUtil;
import com.kakarot.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    /**
     * 根据商品ID查询详情
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 根据商品ID查询商品图片列表
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExp = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    /**
     * 根据商品ID查询商品规格
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemsSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsSpecMapper.selectByExample(itemsSpecExp);
    }

    /**
     * 根据商品ID查询商品
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }

    /**
     * 根据商品ID查询商品的评价等级数量
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {

        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);

        return countsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId,Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if(level != null){
            condition.setCommentLevel(level);
        }

        return itemsCommentsMapper.selectCount(condition);
    }

    /**
     * 根据商品ID查询商品的评价（分页）
     * @param itemId
     * @param level
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,Integer page,Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);

        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        for(ItemCommentVO vo:list){
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }

        return setterPagedGrid(list,page);
    }

    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);

        PageHelper.startPage(page, pageSize);

        List<searchItemsVO> list = itemsMapperCustom.searchItems(map);

        return setterPagedGrid(list,page);
    }

    /**
     * 根据分类搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("catId",catId);
        map.put("sort",sort);

        PageHelper.startPage(page, pageSize);

        List<searchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return setterPagedGrid(list,page);
    }

    /**
     * 根据规格ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIds
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

        String[] ids = specIds.split(",");
        ArrayList<Object> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList,ids);

        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    /**
     * 根据商品规格id获取规格对象的具体信息
     * @param specId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    /**
     * 根据商品id获得商品图片主图url
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }

    /**
     * 减少库存
     * @param specId
     * @param buyCounts
     */
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {

        // synchronized 不推荐使用，集群下无用，性能低下
        // 锁数据库: 不推荐，导致数据库性能低下
        // 分布式锁 zookeeper redis

        // lockUtil.getLock(); -- 加锁

        // 1. 查询库存
        // int stock = 10;

        // 2. 判断库存，是否能够减少到0以下
        // if (stock - buyCounts < 0) {
        // 提示用户库存不够
        // 10 - 3 -3 - 5 = -1
        // }

        // lockUtil.unLock(); -- 解锁


        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        //result即是数据库更新数据的数量。这里如果更新成功，会返回1；没有更新，则返回0
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足!");
        }

    }
}
