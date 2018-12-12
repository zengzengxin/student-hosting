package com.luwei.models.parentchild.pojo.cms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* @author zzx
* @since 2018-12-12
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentChildAddDTO implements Serializable {

private static final long serialVersionUID = 1L;

                
    @ApiModelProperty(value = "家长的id")
                                    @NotNull(message = "家长的id不能为空")
        @Range(min = 0, max = 20000, message = "家长的id范围必须在【{min},{max}】之间")
    private Integer parentId;

    @ApiModelProperty(value = "孩子的id")
                                    @NotNull(message = "孩子的id不能为空")
        @Range(min = 0, max = 20000, message = "孩子的id范围必须在【{min},{max}】之间")
    private Integer childId;

    @ApiModelProperty(value = "创建时间")
                                    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;



}
