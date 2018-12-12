package com.luwei.model.child;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

    List<Child> findChildsByParentsId(Integer id);

}
