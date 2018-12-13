package com.luwei.service.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.order.Order;
import com.luwei.model.order.OrderMapper;
import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.pojo.cms.OrderAddDTO;
import com.luwei.model.order.pojo.cms.OrderQueryDTO;
import com.luwei.model.order.pojo.cms.OrderVO;
import com.luwei.model.order.pojo.web.ConfirmOrderDTO;
import com.luwei.utils.ConversionBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Service
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> {


    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Order findById(Long id) {
        // 若此id已被逻辑删除,也会返回null
        Order order = getById(id);
        // TODO 修改MessageCodes
        Assert.notNull(order, MessageCodes.ORDER_IS_NOT_EXIST);
        return order;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param order
     * @return
     */
    private OrderVO toOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    /**
     * 确认下单/立即购买
     *
     * @param orderDTO
     * @return
     */
    @Transactional
    public OrderVO confirmOrder(ConfirmOrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);

        // 封装课程数据
        // TODO 待完成

        LocalDateTime time = LocalDateTime.now();
        order.setUpdateTime(time);
        order.setCreateTime(time);
        Assert.isTrue(save(order), MessageCodes.ORDER_SAVE_ERROR);
        log.info("保存数据: {}", order);
        return toOrderVO(order);
    }

    /**
     * 批量删除Order
     *
     * @param ids
     */
    @Transactional
    public void deleteOrders(Set<Integer> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.ORDER_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 获取单个Order
     *
     * @param id
     * @return
     */
    public OrderVO getOrder(Long id) {
        Order order = findById(id);
        log.info(order.toString());
        OrderVO orderVO = toOrderVO(order);
        log.info(orderVO.toString());
        return orderVO;
    }

    /**
     * 分页获取Order
     *
     * @param queryDTO
     * @param page
     * @return
     */
    @Transactional
    public IPage<OrderVO> findPage(OrderQueryDTO queryDTO, Page<Order> page) {

        // 先分页查询
        Order order = new Order();
        QueryWrapper<Order> wrapper = new QueryWrapper<>(order);
        if (queryDTO.getServiceName() != null && !queryDTO.getServiceName().equals("")) {
            wrapper.like("service_name", queryDTO.getServiceName());
        }
        if (queryDTO.getOrderStatus() != null) {
            wrapper.eq("order_status", queryDTO.getOrderStatus().getValue());
        }
        IPage<Order> orderIPage = baseMapper.selectPage(page, wrapper);

        // 处理订单: 是否已过期 是否已完成
        orderIPage.setRecords(orderIPage.getRecords().stream().map(this::updateStatus).collect(Collectors.toList()));

        // 返回结果
        return ConversionBeanUtils.conversionBean(orderIPage, this::toOrderVO);
    }

    /**
     * 私有方法 处理订单,判断订单是否已过期或已完成
     * @param order
     * @return
     */
    private Order updateStatus(Order order) {
        if (order.getOrderStatus() == OrderStatusEnum.NOT_PAID) {
            LocalDateTime serviceEndTime = order.getServiceEndTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.compareTo(serviceEndTime) > 0) {
                order.setOrderStatus(OrderStatusEnum.OVERDUE);
                updateById(order);
            }
        }
        if (order.getOrderStatus() == OrderStatusEnum.PAID) {
            LocalDateTime serviceEndTime = order.getServiceEndTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.compareTo(serviceEndTime) > 0) {
                order.setOrderStatus(OrderStatusEnum.COMPLETED);
                updateById(order);
            }
        }
        return order;
    }

    public OrderVO payForOrder(@Valid OrderAddDTO addDTO) {
        return null;
    }
}
