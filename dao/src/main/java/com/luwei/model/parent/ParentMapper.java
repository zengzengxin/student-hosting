package com.luwei.model.parent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.parent.pojo.cms.ParentCmsQueryDTO;
import com.luwei.model.parent.pojo.cms.ParentCmsVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffq
 * @since 2018-12-12
 */
public interface ParentMapper extends BaseMapper<Parent> {

    IPage<ParentCmsVO> findParentPage(@Param("page") Page  page, @Param("parentWebQueryDTO") ParentCmsQueryDTO parentWebQueryDTO);


}
