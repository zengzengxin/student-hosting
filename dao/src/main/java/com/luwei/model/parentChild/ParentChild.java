package com.luwei.model.parentChild;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-12
 */
@ApiModel(value = "" )
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_parent_child" )
public class ParentChild implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id" )
    @TableId(value = "pcid", type = IdType.AUTO)
    private Integer pcid;

    @ApiModelProperty(value = "家长的id" )
    private Integer parentId;

    @ApiModelProperty(value = "孩子的id" )
    private Integer childId;

    @ApiModelProperty(value = "创建时间" )
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间" )
    private LocalDateTime updateTime;
}
