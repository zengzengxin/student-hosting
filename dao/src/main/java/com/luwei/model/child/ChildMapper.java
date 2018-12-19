package com.luwei.model.child;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.child.pojo.cms.ChildQueryDTO;
import com.luwei.model.child.pojo.cms.ChildVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffq
 * @since 2018-12-11
 */
public interface ChildMapper extends BaseMapper<Child> {

    List<ChildVO> findChildsByParentsId(Integer id);


    IPage<ChildVO> findPage(@Param("page") Page<Child> page,@Param("childQueryDTO") ChildQueryDTO childQueryDTO);
}
