package com.luwei.model.teacher;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.model.school.envm.SchoolTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师ID")
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "老师电话")
    private String phone;

    @ApiModelProperty(value = "老师资质执照")
    private String license;

    @ApiModelProperty(value = "老师所在学校")
    private String schoolName;

    @ApiModelProperty(value = "任课年级")
    private String grade;

    @ApiModelProperty(value = "所在班级")
    private String teacherClass;

    @ApiModelProperty(value = "学校的id")
    private Integer schoolId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "是否被绑定")
    @TableLogic
    private Boolean binding;

    @ApiModelProperty(value = "学校类型")
    private SchoolTypeEnum schoolType;

}
