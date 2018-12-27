package com.luwei.model.parent.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-12
 */
@ApiModel(value = "家长")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_parent")
public class ParentQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家长姓名")
    private String parentName;

    @ApiModelProperty(value = "家长联系方式")
    private String phone;


    

}
