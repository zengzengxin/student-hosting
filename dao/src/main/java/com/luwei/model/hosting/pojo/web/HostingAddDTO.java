package com.luwei.model.hosting.pojo.web;

import com.luwei.model.hosting.envm.HostingTypeEnum;
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
 * @since 2018-12-17
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "托管名称")
    @NotBlank(message = "托管名称不能为空")
    @Size(min = 1, max = 12, message = "托管名称长度必须在【{min}和{max}】之间")
    private String name;

    @ApiModelProperty(value = "托管封面")
    @NotBlank(message = "托管封面不能为空")
    @Size(min = 1, max = 12, message = "托管封面长度必须在【{min}和{max}】之间")
    private String coverUrl;

    @ApiModelProperty(value = "托管简介")
    @NotBlank(message = "托管简介不能为空")
    @Size(min = 1, max = 12, message = "托管简介长度必须在【{min}和{max}】之间")
    private String introduction;

    @ApiModelProperty(value = "托管类型")
    @NotNull(message = "托管类型不能为空")
    private HostingTypeEnum hostingType;

    @ApiModelProperty(value = "托管详情")
    @NotBlank(message = "托管详情不能为空")
    @Size(min = 1, max = 12, message = "托管详情长度必须在【{min}和{max}】之间")
    private String details;

    @ApiModelProperty(value = "教师ID")
    @NotNull(message = "教师ID不能为空")
    @Range(min = 0, max = 20000, message = "教师ID范围必须在【{min},{max}】之间")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    @NotBlank(message = "教师名称不能为空")
    @Size(min = 1, max = 12, message = "教师名称长度必须在【{min}和{max}】之间")
    private String teacherName;

    @ApiModelProperty(value = "所在学校id")
    @NotNull(message = "所在学校id不能为空")
    @Range(min = 0, max = 20000, message = "所在学校id范围必须在【{min},{max}】之间")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    @NotBlank(message = "学校名称不能为空")
    @Size(min = 1, max = 12, message = "学校名称长度必须在【{min}和{max}】之间")
    private String schoolName;

    @ApiModelProperty(value = "上架到公众号")
    @NotNull(message = "上架到公众号不能为空")
    @Range(min = 0, max = 20000, message = "上架到公众号范围必须在【{min},{max}】之间")
    private Integer display;

    @ApiModelProperty(value = "是否设为推荐(默认为0)")
    @NotNull(message = "是否设为推荐(默认为0)不能为空")
    @Range(min = 0, max = 20000, message = "是否设为推荐(默认为0)范围必须在【{min},{max}】之间")
    private Integer recommend;

    @ApiModelProperty(value = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @NotBlank(message = "修改时间不能为空")
    @Size(min = 1, max = 12, message = "修改时间长度必须在【{min}和{max}】之间")
    private String updateTime;

    //@ApiModelProperty(value = "是否删除")

}
