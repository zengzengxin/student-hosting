package com.luwei.common.constant;

/**
 * <p>超管操作对象：普通管理员、运营者，普通管理员操作对象：运营者
 *
 * @author luwei
 **/
public enum RoleEnum {

    /**
     * 超管ROOT，拥有最高权限
     */
    ROOT,

    /**
     * 普通管理员ADMIN，拥有部分权限，但比超管权限低
     */
    ADMIN,

    /**
     * 运营者OPERATOR，拥有部分权限，但比普通管理员权限低
     */
    OPERATOR

}
