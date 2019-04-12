package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.property.WechatPayPackage;
import com.luwei.model.course.pojo.web.CourseBuyDTO;
import com.luwei.model.hosting.pojo.web.HostingBuyDTO;
import com.luwei.model.order.Order;
import com.luwei.model.order.pojo.cms.OrderCmsVO;
import com.luwei.model.order.pojo.web.*;
import com.luwei.model.recommend.Recommend;
import com.luwei.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Api(tags = "服务订单模块" )
@RestController
@RequestMapping("/api/order" )
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/confirm" )
    @ApiOperation("确认下单/立即购买（课程）" )
    public OrderCmsVO confirmOrder(@RequestBody @Valid ConfirmOrderDTO orderDTO) {
        return orderService.confirmOrder(orderDTO);
    }

    @PostMapping("/hostingConfirm" )
    @ApiOperation("确认下单/立即购买（托管）" )
    public OrderCmsVO confirmHostingOrder(@RequestBody @Valid HostingOrderDTO hostingOrderDTO) {
        return orderService.hostingOrder(hostingOrderDTO);
    }

    @PostMapping("/recommedBuy" )
    @ApiOperation("下单" )
    public OrderCmsVO recommedBuy(@RequestBody @Valid RecommedBuyDTO recommedBuyDTO) {
        return orderService.recommedBuy(recommedBuyDTO);
    }

    @PostMapping("/hostingBuy" )
    @ApiOperation("下单" )
    public OrderCmsVO hostingBuy(@RequestBody @Valid HostingBuyDTO hostingBuyDTO) {
        return orderService.hostingBuy(hostingBuyDTO);
    }


    @PostMapping("/courseBuy" )
    @ApiOperation("下单" )
    public OrderCmsVO courseBuy(@RequestBody @Valid CourseBuyDTO courseBuyDTO) {
        return orderService.courseBuy(courseBuyDTO);
    }


    @PostMapping("/payFor" )
    @ApiOperation("立即支付" )
    public WechatPayPackage payForOrder(@RequestBody @Valid PayForOrderDTO orderDTO) {
        return orderService.payForOrder(orderDTO);
    }

    @DeleteMapping("delete")
    @ApiOperation("取消订单" )
    public void deleteOrders(@RequestParam @ApiParam("id列表" ) Set<String> ids) {
        orderService.deleteOrders(ids);
    }

    @GetMapping("pay")
    @ApiOperation("立即支付" )
    public boolean pay(@RequestParam("id")  String id) {
       return orderService.pay(id);
    }


    @GetMapping("delete")
    @ApiOperation("立即支付" )
    public void delete(@RequestParam("id")  String id) {
        Set<String> ids = new TreeSet<>();
        ids.add(id);
       orderService.deleteOrders(ids);
    }

    @GetMapping
    @ApiOperation("查询详情" )
    public OrderCmsVO getOrder(@RequestParam @ApiParam("id" ) String id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/page" )
    @ApiOperation("分页获取" )
    public IPage<OrderCmsVO> page(@ModelAttribute MyOrderQueryDTO queryDTO, Page<Order> page) {
        return orderService.findPage(queryDTO, page);
    }

    @GetMapping("findByOrderId")
    @ApiOperation("查询详情" )
    public List<Order> findByOrderId(@RequestParam("parentId") @ApiParam("parentId" ) Integer id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return orderService.findByOrderId(id);
    }

    @GetMapping("/countMoney" )
    @ApiOperation("计算托管价格" )
    public BigDecimal countMoney(@RequestParam("startTime" ) LocalDateTime startTime,
                                 @RequestParam("endTime" ) LocalDateTime endTime, @RequestParam("price" ) BigDecimal price) {
        long days = orderService.getDays(startTime, endTime);
        return (price.multiply(BigDecimal.valueOf(days)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

}
