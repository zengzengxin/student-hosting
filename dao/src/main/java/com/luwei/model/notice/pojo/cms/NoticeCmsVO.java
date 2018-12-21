package com.luwei.model.notice.pojo.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.common.config.ToTimeStampSerializer;
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
public class NoticeCmsVO implements Serializable {


    @ApiModelProperty(value = "通告状态 1上架 0下架")
    private Boolean display;

    @ApiModelProperty(value = "通告的内容")
    private String content;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "公告摘要")
    private String summary;

    @ApiModelProperty(value = "公告类型，0公告平台，1教育局平台")
    private String type;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime time;





}
