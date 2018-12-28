package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.order.Order;
import com.luwei.model.order.pojo.cms.OrderCmsVO;
import com.luwei.model.order.pojo.cms.OrderQueryDTO;
import com.luwei.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Api(tags = {"课程订单模块", "托管订单模块"})
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public void deleteOrders(@RequestParam @ApiParam("id列表") Set<String> ids) {
        orderService.deleteOrders(ids);
    }

    @GetMapping
    @ApiOperation("查询详情")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public OrderCmsVO getOrder(@RequestParam @ApiParam("id") String id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public IPage<OrderCmsVO> page(@ModelAttribute OrderQueryDTO queryDTO, Page<Order> page) {
        return orderService.findPage(queryDTO, page);
    }

}
