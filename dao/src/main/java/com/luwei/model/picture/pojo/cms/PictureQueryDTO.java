package com.luwei.model.picture.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @author zzx
* @since 2018-12-19
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
public class PictureQueryDTO implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片ID")
                    private Integer pictureId;

    @ApiModelProperty(value = "图片url")
                    private String pictureUrl;

    @ApiModelProperty(value = "图片类型 0-课程 1-托管 2-菜品")
                    private Integer pictureType;

    @ApiModelProperty(value = "外键ID 可以是课程ID 托管ID 等")
                    private Integer foreignKeyId;

    @ApiModelProperty(value = "创建时间")
                    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
                    private LocalDateTime updateTime;

            


}
