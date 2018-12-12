package com.luwei.plus;

import lombok.Data;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 15:39 2018/11/8
 */
@Data
public class PropertiesBean {
    private String url;

    private String driverClassName;

    private String username;

    private String password;

    //基础包名
    private String packageName;

    //表名
    private String[] tableNames;

    //表前缀
    private String tablePrefix;

    //逻辑删除字段名称
    private String logicDeleteFieldName;

    //乐观锁字段名称
    private String versionFieldName;

    //是否生成restController
    private Boolean restControllerStyle;

    //注释作者名称
    private String author;

    private Boolean isOverEntity;
    private Boolean isOverController;
    private Boolean isOverService;
    private Boolean isOverServiceImpl;
    private Boolean isOverMapper;
    private Boolean isOverXml;


    private String entityVM = "/templates/entity.vm";
    private String controllerVM = "/templates/controller.vm";
    private String serviceVM = "";
    private String serviceImplVM = "";
    private String mapperVM = "";
    private String xmlVM = "";

    //生成的实体包名
    private String beanNames = "";


    //VO、DTO位置
    private String voDTODir = "";
}
