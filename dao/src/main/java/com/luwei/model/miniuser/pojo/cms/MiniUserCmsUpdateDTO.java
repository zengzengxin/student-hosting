package com.luwei.model.miniuser.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableField;
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

/**
 * @author ffq
 * @since 2018-12-20
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MiniUserCmsUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @NotNull(message = "主键id不能为空")
    @Range(min = 0, max = 20000, message = "主键id范围必须在【{min},{max}】之间")
    private Integer miniUserId;

    @ApiModelProperty(value = "oppenid")
    @NotBlank(message = "oppenid不能为空")
    @Size(min = 1, max = 100, message = "oppenid长度必须在【{min}和{max}】之间")
    private String openId;

    @ApiModelProperty(value = "头像")
    @NotBlank(message = "头像不能为空")
    @Size(min = 1, max = 100, message = "头像长度必须在【{min}和{max}】之间")
    private String avatarUrl;

    @ApiModelProperty(value = "昵称")
    @TableField("nickName")
    @NotBlank(message = "昵称不能为空")
    @Size(min = 1, max = 100, message = "昵称长度必须在【{min}和{max}】之间")
    private String nickName;

    @ApiModelProperty(value = "性别 0为不确定，1为男，2为女 默认为0")
    @NotNull(message = "性别 0为不确定，1为男，2为女 默认为0不能为空")
    @Range(min = 0, max = 20000, message = "性别 0为不确定，1为男，2为女 默认为0范围必须在【{min},{max}】之间")
    private Integer gender;

}
