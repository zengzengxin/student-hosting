package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.order.Order;
import com.luwei.model.order.pojo.cms.OrderQueryDTO;
import com.luwei.model.order.pojo.cms.OrderVO;
import com.luwei.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    /*@PostMapping
    @ApiOperation("新增")
    public OrderVO saveOrder(@RequestBody @Valid OrderAddDTO addDTO) {
        return orderService.saveOrder(addDTO);
    }*/

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteOrders(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
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

}
