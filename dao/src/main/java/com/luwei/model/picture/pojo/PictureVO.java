package com.luwei.model.picture.pojo;

import com.luwei.model.picture.envm.PictureTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PictureVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片ID")
    private Integer pictureId;

    @ApiModelProperty(value = "图片url")
    private String pictureUrl;

    @ApiModelProperty(value = "图片类型")
    private PictureTypeEnum pictureType;

}
