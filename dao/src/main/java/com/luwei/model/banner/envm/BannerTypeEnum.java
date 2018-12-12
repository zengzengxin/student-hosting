package com.luwei.model.banner.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Author: huanglp
 * Date: 2018-12-07
 */
public enum BannerTypeEnum implements IEnum {
    /**
     * 0-首页
     */
    HOME_PAGE(0),
    /**
     * 1-订课
     */
    BOOKING_COURSE(1),
    /**
     * 2-点餐
     */
    ORDER_FOOD(2);

    private int value;

    BannerTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
