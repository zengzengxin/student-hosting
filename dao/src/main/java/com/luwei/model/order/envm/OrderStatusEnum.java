package com.luwei.model.order.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
public enum OrderStatusEnum implements IEnum<Integer> {
    /**
     * 0-待付款
     */
    NOT_PAID(0),
    /**
     * 1-已付款
     */
    PAID(1),
    /**
     * 2-已完成
     */
    COMPLETED(2),
    /**
     * 3-失效过期
     */
    OVERDUE(3);

    private int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
