package com.luwei.model.school.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author huanglp
 * Date: 2018-12-13
 */

public enum SchoolTypeEnum implements IEnum {
    /**
     * 0-小学
     */

    PRIMARY_SCHOOL(0),
    /**
     * 1-培训机构
     */
    TRINING_INSTITUTION(1);

    private int value;

    SchoolTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
