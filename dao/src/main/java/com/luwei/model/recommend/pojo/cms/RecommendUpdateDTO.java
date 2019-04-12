package com.luwei.model.recommend.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_recommend" )
public class RecommendUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推荐表ID" )
    @TableId(value = "recommend_id", type = IdType.AUTO)
    @NotNull(message = "推荐表ID不能为空" )
    private Integer recommendId;


    @ApiModelProperty(value = "权重" )
    @NotNull(message = "权重不能为空" )
    private Integer weight;


}
