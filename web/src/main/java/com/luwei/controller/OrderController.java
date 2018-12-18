package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.order.Order;
import com.luwei.model.order.pojo.cms.OrderQueryDTO;
import com.luwei.model.order.pojo.cms.OrderVO;
import com.luwei.model.order.pojo.web.ConfirmOrderDTO;
import com.luwei.model.order.pojo.web.HostingOrderDTO;
import com.luwei.model.order.pojo.web.PayForOrderDTO;
import com.luwei.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    @ApiOperation("确认下单/立即购买(课程)")
    public OrderVO confirmOrder(@RequestBody @Valid ConfirmOrderDTO orderDTO) {
        return orderService.confirmOrder(orderDTO);
    }

    @PostMapping("/payFor")
    @ApiOperation("立即支付")
    public OrderVO payForOrder(@RequestBody @Valid PayForOrderDTO orderDTO) {
        return orderService.payForOrder(orderDTO);
    }

    @DeleteMapping
    @ApiOperation("取消订单")
    public void deleteOrders(@RequestParam @ApiParam("id列表") Set<String> ids) {
        orderService.deleteOrders(ids);
    }

    @GetMapping
    @ApiOperation("查询详情")
    public OrderVO getOrder(@RequestParam @ApiParam("id") Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<OrderVO> page(@ModelAttribute OrderQueryDTO queryDTO, Page<Order> page) {
        return orderService.findPage(queryDTO, page);
    }

    //创建托管订单
    @PostMapping("/hostingConfirm")
    @ApiOperation("确认下单/立即购买（托管）")
    public OrderVO confirmHostingOrder(@RequestBody @Valid HostingOrderDTO hostingOrderDTO) {
        return orderService.hostingOrder(hostingOrderDTO);
    }
}
