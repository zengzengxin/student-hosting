package com.luwei.model.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 刷新未支付(not paid)订单的状态 -> 满足条件 -> 已失效
     */
    long refreshNotPaidOrderStatus();

    /**
     * 刷新已支付(paid)订单的状态 -> 满足条件 -> 已完成
     */
    long refreshPaidOrderStatus();

    /**
     * 使用case关键字 刷新订单状态. 一条sql可搞定
     */
    long useCaseFreshOrderStatus();

}
