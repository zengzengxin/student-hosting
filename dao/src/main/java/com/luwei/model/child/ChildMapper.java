package com.luwei.model.child;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.child.pojo.cms.ChildQueryDTO;
import com.luwei.model.child.pojo.cms.ChildCmsVO;
import com.luwei.model.child.pojo.web.ChildBindingDTO;
import com.luwei.model.child.pojo.web.ChildWebVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zzx
 * @since 2018-12-11
 */
public interface ChildMapper extends BaseMapper<Child> {

    List<ChildCmsVO> findChildsByParentsId(Integer id);

    List<ChildWebVO> webFindChildsByParentsId(Integer id);


    IPage<ChildCmsVO> findPage(@Param("page" ) Page<Child> page, @Param("childQueryDTO" ) ChildQueryDTO childQueryDTO,
                               @Param("schoolId" ) Integer schoolId);

    Child findChildByStunoAndNameAndSchoolId(@Param("childBindingDTO" ) ChildBindingDTO childBindingDTO);


}
