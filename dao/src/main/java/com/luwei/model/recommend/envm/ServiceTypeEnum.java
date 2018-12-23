package com.luwei.model.recommend.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Author: huanglp
 * Date: 2018-12-23
 */
public enum ServiceTypeEnum implements IEnum<Integer> {

    /**
     * 0-首页
     */
    COURSE(0),

    /**
     * 1-订课
     */
    HOSTING(1);

    private int value;

    ServiceTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
