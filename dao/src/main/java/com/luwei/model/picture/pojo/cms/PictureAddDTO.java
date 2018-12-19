package com.luwei.model.picture.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @author zzx
* @since 2018-12-19
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PictureAddDTO implements Serializable {

private static final long serialVersionUID = 1L;

                
    @ApiModelProperty(value = "图片url")
                                    @NotBlank(message = "图片url不能为空")
        @Size(min = 1, max = 12, message = "图片url长度必须在【{min}和{max}】之间")
    private String pictureUrl;

    @ApiModelProperty(value = "图片类型 0-课程 1-托管 2-菜品")
                                    @NotNull(message = "图片类型 0-课程 1-托管 2-菜品不能为空")
        @Range(min = 0, max = 20000, message = "图片类型 0-课程 1-托管 2-菜品范围必须在【{min},{max}】之间")
    private Integer pictureType;

    @ApiModelProperty(value = "外键ID 可以是课程ID 托管ID 等")
                                    @NotNull(message = "外键ID 可以是课程ID 托管ID 等不能为空")
        @Range(min = 0, max = 20000, message = "外键ID 可以是课程ID 托管ID 等范围必须在【{min},{max}】之间")
    private Integer foreignKeyId;

    @ApiModelProperty(value = "创建时间")
                                    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
                                    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updateTime;

                


}
