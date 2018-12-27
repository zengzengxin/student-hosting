package com.luwei.model.miniuser;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-20
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_mini_user")
public class MiniUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "mini_user_id", type = IdType.AUTO)
    private Integer miniUserId;

    @ApiModelProperty(value = "oppenid")
    private String openId;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "性别 0为不确定，1为男，2为女 默认为0")
    private Integer gender;

    @ApiModelProperty(value = "老师id")
    private Integer teacherId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean deleted;

}
