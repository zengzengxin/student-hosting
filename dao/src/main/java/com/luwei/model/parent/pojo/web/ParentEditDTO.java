package com.luwei.model.parent.pojo.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家长ID" )
    private Integer parentId;

    @ApiModelProperty(value = "家长联系方式" )
    @NotBlank(message = "家长联系方式不能为空" )
    private String phone;

    @ApiModelProperty(value = "家长姓名" )
    private String parentName;

    @ApiModelProperty(value = "家庭关系" )
    private String familyRelation;

    @ApiModelProperty(value = "家庭住址" )
    private String address;

}
