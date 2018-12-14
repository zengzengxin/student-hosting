package com.luwei.model.child.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ffq
 * @since 2018-12-11
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的主键id")
    @TableId(value = "child_id", type = IdType.AUTO)
    private Integer childId;

    @ApiModelProperty(value = "孩子的生日")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "孩子的年级")
    private String grade;

    @ApiModelProperty(value = "孩子的班级")
    private String ChildClass;

    @ApiModelProperty(value = "孩子的班主任的电话")
    private String headteacherPhone;

    @ApiModelProperty(value = "孩子的班主任的姓名")
    private String headteacherName;



}
