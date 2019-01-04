package com.luwei.common.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>平台管理员操作对象：教育局管理员、学校/机构管理员
 * @author luwei
 **/
public enum RoleEnum implements IEnum<Integer> {

    /**
     * 平台管理员ROOT，拥有最高权限
     */
    ROOT(0),

    /**
     * 教育局管理员
     */
    ADMIN(1),

    /**
     * 学校/机构管理员
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
