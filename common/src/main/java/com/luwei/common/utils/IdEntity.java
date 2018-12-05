package com.luwei.common.utils;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;


/**
 * 所有持久化实体的超类
 *
 * @author luwei
 **/
@Data
@MappedSuperclass
public class IdEntity implements Serializable {

    @Column(columnDefinition = "timestamp not null default current_timestamp comment '创建时间'")
    protected Date createTime = new Date();

    @Column(columnDefinition = "timestamp not null default current_timestamp on update current_timestamp comment '修改时间'")
    protected Date updateTime = new Date();

    @Column(columnDefinition = "bit not null default 0 comment '删除状态true:已删除,false:未删除'")
    protected Boolean deleted = false;

}
