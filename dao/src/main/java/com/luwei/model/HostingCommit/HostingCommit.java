package com.luwei.model.HostingCommit;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel(value = "" )
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_hosting_commit" )
public class HostingCommit {

    private static final long serialVersionUID = 1L;

    @TableId(value = "hosting_commit_id", type = IdType.AUTO)
    private Integer HostingCommitId;

    private Integer parentId;

    private Integer hostingId;

    private String contant;

    private String parentName;


    @ApiModelProperty(value = "是否删除" )
    @TableLogic
    private Integer deleted;

}
