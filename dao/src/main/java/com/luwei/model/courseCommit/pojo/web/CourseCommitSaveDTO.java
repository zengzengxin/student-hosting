package com.luwei.model.courseCommit.pojo.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CourseCommitSaveDTO {
    private Integer parentId;

    private Integer courseId;

    private String contant;
}
