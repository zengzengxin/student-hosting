package com.luwei.model.notice.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.luwei.common.config.ToTimeStampSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author huanglp
 * Date: 2018-12-06
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NoticeQueryDTO {

    @ApiModelProperty(value = "通告状态 1上架 0下架")
    private Integer noticeStatus;

    @ApiModelProperty(value = "公告标题")
    private String noticeTittle;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;


}
