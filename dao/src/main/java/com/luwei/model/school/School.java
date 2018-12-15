package com.luwei.model.school;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.model.school.envm.schoolTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-13
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校id")
    @TableId(value = "school_id", type = IdType.AUTO)
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String name;

    @ApiModelProperty(value = "学校介绍")
    private String introduction;

    @ApiModelProperty(value = "学校编码")
    private String code;

    @ApiModelProperty(value = "负责人电话")
    private String leaderPhone;

    @ApiModelProperty(value = "学校执照")
    private String license;

    @ApiModelProperty(value = "学生人数")
    private Integer studentNumber;

    @ApiModelProperty(value = "学校类型(0/1)")
    private schoolTypeEnum type;

    @ApiModelProperty(value = "权限(0/1)")
    private Integer permission;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否已删除 0-否 1-是")
    @TableLogic
    private Boolean deleted;

}
