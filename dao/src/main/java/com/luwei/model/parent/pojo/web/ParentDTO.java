package com.luwei.model.parent.pojo.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @author ffq
* @since 2018-12-12
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_parent")
public class ParentDTO implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家长ID")
            @TableId(value = "parent_id", type = IdType.AUTO)
                    private Integer parentId;

    @ApiModelProperty(value = "家长姓名")
            private String parentName;

    @ApiModelProperty(value = "家长联系方式")
            private String phone;

    @ApiModelProperty(value = "家庭关系")
            private String familyRelation;


    @ApiModelProperty(value = "家庭住址")
            private String address;

    @ApiModelProperty(value = "家长头像")
            private String avatarUrl;

    @ApiModelProperty(value = "家长性别,0代表男，1代表女,默认为0")
            private Integer gender;

    @ApiModelProperty(value = "家长昵称")
            private String nickName;

    @ApiModelProperty(value = "微信用户openid")
            private String openid;

    @ApiModelProperty(value = "创建时间")
            private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
            private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，1删除，0未删除")
            @TableLogic
    private Boolean deleted;



}
