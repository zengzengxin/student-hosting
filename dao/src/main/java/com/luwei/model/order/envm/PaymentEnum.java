package com.luwei.model.order.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
public enum PaymentEnum implements IEnum<Integer> {
    /**
     * 0-微信支付
     */
    WECHAT(0),
    /**
     * 1-支付宝支付
     */
    ALIPAY(1);

    private int value;

    PaymentEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
