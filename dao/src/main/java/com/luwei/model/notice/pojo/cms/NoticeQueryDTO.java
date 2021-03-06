package com.luwei.model.notice.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author huanglp
 * Date: 2018-12-06
 */
@ApiModel(value = "公告" )
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryDTO {

    @ApiModelProperty(value = "通告状态 1上架 0下架" )
    private Boolean display;

    @ApiModelProperty(value = "公告标题" )
    private String title;

    @ApiModelProperty(value = "开始时间" )
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间" )
    private LocalDateTime endTime;


}
