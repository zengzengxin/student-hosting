package com.luwei.service.notice.pojos;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ffq
 * @since 2018-12-05
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_notice")
public class NoticeDTO implements Serializable {

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


}