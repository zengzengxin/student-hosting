package com.luwei.model.picture.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Author: huanglp
 * Date: 2018-12-12
 */
public enum PictureTypeEnum implements IEnum {
    /**
     * 0-课程
     */
    COURSE(0),
    /**
     * 1-托管
     */
    HOSTING(1),
    /**
     * 2-菜谱
     */
    FOOD(2);

    private int value;

    PictureTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
