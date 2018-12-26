package com.luwei.model.notice.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ffq
 * @since 2018-12-05
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NoticeAddDTO implements Serializable {

    @ApiModelProperty(value = "通告状态 1上架 0下架")
    @NotNull(message = "通告状态不能为空")
    private Boolean display;

    @ApiModelProperty(value = "通告的内容")
    @NotBlank(message = "通告的内容不能为空")
    private String content;

    @ApiModelProperty(value = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    @ApiModelProperty(value = "公告摘要")
    @NotBlank(message = "公告摘要不能为空")
    private String summary;




}