package com.luwei.service.manager.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author jdq
 * @date 2017/12/9 17:46
 */
@Data
public class ManagerEditVO {

    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Integer managerId;

    @ApiModelProperty("名称")
    @NotBlank(message = "请输入管理员名称")
    @Length(max = 20, message = "名称最大长度限制为20")
    private String name;

}
