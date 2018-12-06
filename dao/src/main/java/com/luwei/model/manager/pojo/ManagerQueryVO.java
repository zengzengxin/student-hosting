package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description
 * create by LW-mochengdong
 * 2018/7/24
 */
@Data
public class ManagerQueryVO {

    @ApiModelProperty("主键id")
    private Integer managerId;

    @ApiModelProperty("名称")
    private String name;

}
