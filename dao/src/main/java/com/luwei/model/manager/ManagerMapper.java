package com.luwei.model.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.manager.pojo.ManagerPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-24
 */
public interface ManagerMapper extends BaseMapper<Manager> {

    IPage<ManagerPageVO> selectManagerPage(Page page,
                                           @Param("managerId" ) Integer managerId,
                                           @Param("name" ) String name);

    List<Manager> selectForSpecialCondition();

}
