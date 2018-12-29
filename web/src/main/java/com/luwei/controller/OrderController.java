package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.property.WechatPayPackage;
import com.luwei.model.order.Order;
import com.luwei.model.order.pojo.cms.OrderCmsVO;
import com.luwei.model.order.pojo.web.ConfirmOrderDTO;
import com.luwei.model.order.pojo.web.HostingOrderDTO;
import com.luwei.model.order.pojo.web.MyOrderQueryDTO;
import com.luwei.model.order.pojo.web.PayForOrderDTO;
import com.luwei.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Api(tags = "服务订单模块")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/confirm")
    @ApiOperation("确认下单/立即购买（课程）")
    public OrderCmsVO confirmOrder(@RequestBody @Valid ConfirmOrderDTO orderDTO) {
        return orderService.confirmOrder(orderDTO);
    }

    @PostMapping("/payFor")
    @ApiOperation("立即支付")
    public WechatPayPackage payForOrder(@RequestBody @Valid PayForOrderDTO orderDTO) {
        return orderService.payForOrder(orderDTO);
    }

    @DeleteMapping
    @ApiOperation("取消订单")
    public void deleteOrders(@RequestParam @ApiParam("id列表") Set<String> ids) {
        orderService.deleteOrders(ids);
    }

    @GetMapping
    @ApiOperation("查询详情")
    public OrderCmsVO getOrder(@RequestParam @ApiParam("id") String id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<OrderCmsVO> page(@ModelAttribute MyOrderQueryDTO queryDTO, Page<Order> page) {
        return orderService.findPage(queryDTO, page);
    }

    //创建托管订单
    @PostMapping("/hostingConfirm")
    @ApiOperation("确认下单/立即购买（托管）")
    public OrderCmsVO confirmHostingOrder(@RequestBody @Valid HostingOrderDTO hostingOrderDTO) {
        return orderService.hostingOrder(hostingOrderDTO);
    }

    //创建托管订单
    @GetMapping("/countMoney")
    @ApiOperation("计算托管价格")
    public int countMoney(@RequestParam("startTime") LocalDateTime startTime, @RequestParam("endTime") LocalDateTime endTime, @RequestParam("price") int price) {
        long days =  orderService.getDays(startTime,endTime);
        return (int)(days*price);

    }

}
