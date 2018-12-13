package com.luwei.model.teacher.pojo.web;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* @author zzx
* @since 2018-12-12
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentChildVO implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
                    private Integer pcid;

    @ApiModelProperty(value = "家长的id")
                    private Integer parentId;

    @ApiModelProperty(value = "孩子的id")
                    private Integer childId;

    @ApiModelProperty(value = "创建时间")
                    private LocalDateTime createTime;



}
