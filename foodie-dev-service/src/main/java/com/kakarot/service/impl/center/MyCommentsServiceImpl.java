package com.kakarot.service.impl.center;

import com.kakarot.enums.YesOrNo;
import com.kakarot.mapper.ItemsCommentsMapperCustom;
import com.kakarot.mapper.OrderItemsMapper;
import com.kakarot.mapper.OrderStatusMapper;
import com.kakarot.mapper.OrdersMapper;
import com.kakarot.pojo.OrderItems;
import com.kakarot.pojo.OrderStatus;
import com.kakarot.pojo.Orders;
import com.kakarot.pojo.bo.center.OrderItemsCommentBO;
import com.kakarot.service.center.MyCommentsService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    public OrderItemsMapper orderItemsMapper;
    @Autowired
    public OrdersMapper ordersMapper;
    @Autowired
    public OrderStatusMapper orderStatusMapper;
    @Autowired
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    private Sid sid;

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
            //设置主键id
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);

    }
}
