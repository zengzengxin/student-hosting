package com.luwei.model.banner.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-07
 */
public enum BannerTypeEnum implements IEnum {
    /**
     * 首页
     */
    HOME_PAGE(0),
    /**
     * 订课
     */
    BOOK_LESSON(1),
    /**
     * 点餐
     */
    ORDER_FOOD(2);

    private int value;

    BannerTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

}
