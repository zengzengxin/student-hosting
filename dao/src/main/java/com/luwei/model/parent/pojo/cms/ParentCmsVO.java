package com.luwei.model.parent.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.model.child.pojo.web.ChildVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author ffq
 * @since 2018-12-12
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_parent")
public class ParentCmsVO implements Serializable {

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

    @ApiModelProperty(value = "子女个数")
    private Integer childNumber;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "孩子的集合")
    private List<ChildVO> ListChild;

}
