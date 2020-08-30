package com.kakarot.controller;

import com.kakarot.config.WebMvcConfig;
import com.kakarot.enums.OrderStatusEnum;
import com.kakarot.enums.PayMethod;
import com.kakarot.pojo.OrderStatus;
import com.kakarot.pojo.bo.ShopcartBO;
import com.kakarot.pojo.bo.SubmitOrderBO;
import com.kakarot.pojo.vo.MerchantOrdersVO;
import com.kakarot.pojo.vo.OrderVO;
import com.kakarot.service.OrderService;
import com.kakarot.utils.CookieUtils;
import com.kakarot.utils.IMOOCJSONResult;
import com.kakarot.utils.JsonUtils;
import com.kakarot.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value="订单相关",tags={"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value="用户下单",notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response){

        if(!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)
            && !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)){
            return IMOOCJSONResult.errorMsg("支付方式不支持！");
        }

        //System.out.println(submitOrderBO.toString());

        String shopcartJson = redisOperator.get(FOODIE_SHOPCART+":"+submitOrderBO.getUserId());
        if(StringUtils.isBlank(shopcartJson)){
            return IMOOCJSONResult.errorMsg("购物车数据不正确");
        }

        List<ShopcartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);

        //1、创建订单
        OrderVO orderVO = orderService.createOrder(shopcartList,submitOrderBO);
        String orderId = orderVO.getOrderId();

        //2、创建订单以后，移除购物车中已结算（已提交）的商品
        /**
         * 1001
         * 2002 -> 用户购买
         * 3003 -> 用户购买
         * 4004
         */
        //清理覆盖现有的redis汇总的购物数据
        shopcartList.removeAll(orderVO.getToBeRemovedShopCartList());
        redisOperator.set(FOODIE_SHOPCART+":"+submitOrderBO.getUserId(),JsonUtils.objectToJson(shopcartList));

        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        //3、向支付中心发送当前订单，用于保存支付中心的订单数据
            //请求体的内容
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        //为了方便测试购买，所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);

            //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId","imooc");
        headers.add("password","imooc");

            //请求
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

            //发送请求。三个参数：请求路径、请求、响应类型
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity, IMOOCJSONResult.class);
            //拿到响应
        IMOOCJSONResult paymentResult = responseEntity.getBody();
        if(paymentResult.getStatus() != 200){
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }

        return IMOOCJSONResult.ok(orderId);

    }

    /**
     *支付成功之后回调此接口，更改订单状态
     */
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    /**
     * 轮询订单状态。
     * 到支付页面后，前端每三秒调用一次此接口，来查询订单是否已支付
     */
    @PostMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }

}
