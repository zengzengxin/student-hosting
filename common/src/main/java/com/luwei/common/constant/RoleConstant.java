package com.luwei.common.constant;

/**
 *
 * @author luwei
 **/
public interface RoleConstant {

    /**
     * 超级管理员
     */
    String ROOT = "0";

    /**
     * 普通管理员
     */
    String ADMIN = "1";

    /**
     * 运营者OPERATOR，拥有部分权限，但比普通管理员权限低
     */
    String OPERATOR = "2";

}
