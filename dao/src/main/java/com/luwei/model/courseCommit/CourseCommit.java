package com.luwei.model.courseCommit;

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

@ApiModel(value = "" )
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_course_commit" )
public class CourseCommit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "commit_id", type = IdType.AUTO)
    private Integer commitId;

    private Integer parentId;

    private Integer courseId;

    private String contant;

    private String parentName;


    @ApiModelProperty(value = "是否删除" )
    @TableLogic
    private Integer deleted;
}
