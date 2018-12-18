package com.luwei.model.hosting.pojo.cms;

import com.luwei.model.hosting.envm.HostingTypeEnum;
import com.luwei.model.hostingPackage.pojo.cms.HostingPackageAddDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
    private String name;

    @ApiModelProperty(value = "托管封面")
    @NotBlank(message = "托管封面不能为空")
    private String cover;

    @ApiModelProperty(value = "托管简介")
    @NotBlank(message = "托管简介不能为空")
    private String introduction;

    @ApiModelProperty(value = "托管类型")
    @NotNull(message = "托管类型不能为空")
    private HostingTypeEnum hostingType; //枚举

    @ApiModelProperty(value = "托管详情")
    @NotBlank(message = "托管详情不能为空")
    private String details;

    @ApiModelProperty(value = "教师ID")
    @NotNull(message = "教师ID不能为空")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    @NotBlank(message = "教师名称不能为空")
    private String teacherName;

    @ApiModelProperty(value = "所在学校id")
    @NotNull(message = "所在学校id不能为空")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    @NotBlank(message = "学校名称不能为空")
    private String schoolName;

    @ApiModelProperty(value = "上架到公众号")
    @NotNull(message = "上架到公众号不能为空")
    private Boolean display;

    @ApiModelProperty(value = "是否设为推荐(默认为0)")
    @NotNull(message = "是否设为推荐(默认为0)不能为空")
    private Boolean recommend;


    @NotNull
    @ApiModelProperty(value = "课程图片ID(最多3张)")
    private List<String> pictureUrls;

    @ApiModelProperty(value = "课程套餐列表")
    private List<HostingPackageAddDTO> hostingPackageList;

}
