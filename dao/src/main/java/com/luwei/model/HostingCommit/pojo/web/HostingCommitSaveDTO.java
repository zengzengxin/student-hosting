package com.luwei.model.HostingCommit.pojo.web;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HostingCommitSaveDTO {
    private Integer parentId;

    private Integer courseId;

    private String contant;
}
