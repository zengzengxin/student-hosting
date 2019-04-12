package com.luwei.model.hosting.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author zzx
 * Date: 2018-12-17
 */
public enum HostingTypeEnum implements IEnum {


    /**
     * 0-全托
     */

    FULL_HOSTING(0),

    /**
     * 1-午托
     */
    MIDDAY_HOSTING(1),

    /**
     * 2-晚托
     */
    NIGHT_HOSTING(2);

    private int value;

    HostingTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
