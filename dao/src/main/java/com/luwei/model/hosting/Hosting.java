package com.luwei.model.hosting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-17
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_hosting")
public class Hosting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "托管ID")
    @TableId(value = "hosting_id", type = IdType.AUTO)
    private Integer hostingId;

    @ApiModelProperty(value = "托管名称")
    private String name;

    @ApiModelProperty(value = "托管封面")
    private String cover;

    @ApiModelProperty(value = "托管简介")
    private String introduction;

    @ApiModelProperty(value = "托管类型")
    private Integer hostingType;

    @ApiModelProperty(value = "托管详情")
    private String details;

    @ApiModelProperty(value = "教师ID")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "所在学校id")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "上架到公众号")
    private Integer display;

    @ApiModelProperty(value = "是否设为推荐(默认为0)")
    private Integer recommend;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;



}