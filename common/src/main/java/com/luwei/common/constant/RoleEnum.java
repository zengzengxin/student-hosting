package com.luwei.common.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>超管操作对象：普通管理员、运营者，普通管理员操作对象：运营者
 *
 * @author luwei
 **/
public enum RoleEnum implements IEnum<Integer> {

    /**
     * 超管ROOT，拥有最高权限
     */
    ROOT(0),

    /**
     * 普通管理员ADMIN，拥有部分权限，但比超管权限低
     */
    ADMIN(1),

    /**
     * 运营者OPERATOR，拥有部分权限，但比普通管理员权限低
     */
    OPERATOR(2);

    private int value;

    RoleEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
