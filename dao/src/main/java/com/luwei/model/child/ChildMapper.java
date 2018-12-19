package com.luwei.model.child;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luwei.model.child.pojo.web.ChildVO;

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

}
