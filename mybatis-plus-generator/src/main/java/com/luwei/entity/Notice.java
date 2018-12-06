package com.luwei.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* @author ffq
* @since 2018-12-05
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_notice")
public class Notice implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
            @TableId(value = "notice_id", type = IdType.AUTO)
                    private Integer noticeId;

    @ApiModelProperty(value = "通告状态 1上架 0下架")
            private Integer noticeStatus;

    @ApiModelProperty(value = "通告的内容")
            private String noticeContent;

    @ApiModelProperty(value = "公告标题")
            private String noticeTittle;

    @ApiModelProperty(value = "公告摘要")
            private String noticeSummary;

    @ApiModelProperty(value = "公告生效时间")
            private LocalDateTime effectiveTime;

    @ApiModelProperty(value = "公告失效时间")
            private LocalDateTime failureTime;

    @ApiModelProperty(value = "创建时间")
            private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
            private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除 0未删除 1删除")
            @TableLogic
    private Integer deleted;



}
