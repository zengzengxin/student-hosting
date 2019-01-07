package com.luwei.model.institution;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2019-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_institution")
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构ID")
    @TableId(value = "institution_id", type = IdType.AUTO)
    private Integer institutionId;

    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "机构介绍")
    private String introduction;

    @ApiModelProperty(value = "负责人姓名")
    private String leaderName;

    @ApiModelProperty(value = "负责人电话")
    private String leaderPhone;

    @ApiModelProperty(value = "机构执照")
    private String license;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean deleted;

}
