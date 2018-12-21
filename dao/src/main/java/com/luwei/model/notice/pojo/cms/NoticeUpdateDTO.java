package com.luwei.model.notice.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author huanglp
 * Date: 2018-12-06
 */

@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NoticeUpdateDTO {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    @ApiModelProperty(value = "通告状态 1上架 0下架")
    private Boolean display;

    @ApiModelProperty(value = "通告的内容")
    private String content;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "公告摘要")
    private String summary;


}
