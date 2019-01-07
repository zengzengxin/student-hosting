package com.luwei.model.coursepackage.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_course_package")
public class CoursePackageQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程套餐ID")
    @TableId(value = "course_package_id", type = IdType.AUTO)
    private Integer coursePackageId;

    @ApiModelProperty(value = "课程开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "课程结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数")
    private Integer maxNumber;

    @ApiModelProperty(value = "上架状态")
    private Boolean display;

    @ApiModelProperty(value = "是否过期")
    private Boolean overdue;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean deleted;

}
