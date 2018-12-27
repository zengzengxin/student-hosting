package com.luwei.service.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.order.Order;
import com.luwei.model.order.OrderMapper;
import com.luwei.model.order.pojo.cms.OrderAddDTO;
import com.luwei.model.order.pojo.cms.OrderQueryDTO;
import com.luwei.model.order.pojo.cms.OrderCmsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Order findById(String id) {
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
    private OrderCmsVO toOrderVO(Order order) {
        OrderCmsVO orderVO = new OrderCmsVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    /**
     * 新增Order
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderCmsVO saveOrder(OrderAddDTO addDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(addDTO, order);
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
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrders(Set<String> ids) {
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
    public OrderCmsVO getOrder(String id) {
        Order order = findById(id);
        log.info(order.toString());
        OrderCmsVO orderVO = toOrderVO(order);
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
    public IPage<OrderCmsVO> findPage(OrderQueryDTO queryDTO, Page<Order> page) {
        LambdaQueryWrapper<Order> wrapper = new QueryWrapper<Order>().lambda();

        if (queryDTO.getServiceName() != null && !queryDTO.getServiceName().equals("")) {
            wrapper.like(Order::getServiceName, queryDTO.getServiceName());
        }
        wrapper.eq(Order::getOrderType, queryDTO.getOrderType());
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toOrderVO);
    }

}
