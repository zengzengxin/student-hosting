package com.luwei.model.order.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
public enum  OrderTypeEnum implements IEnum<Integer> {
    /**
     * 0-课程
     */
    COURSE(0),
    /**
     * 1-托管
     */
    HOSTING(1),
    /**
     * 2-餐品
     */
    FOOD(2);

    private int value;

    OrderTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
