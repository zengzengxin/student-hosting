package com.luwei.model.school;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.school.pojo.cms.SchoolQueryDTO;
import com.luwei.model.school.pojo.cms.SchoolCmsVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2018-12-13
 */
public interface SchoolMapper extends BaseMapper<School> {


    IPage<SchoolCmsVO> getSchoolPage(@Param("page" ) Page page, @Param("schoolQueryDTO" ) SchoolQueryDTO schoolQueryDTO);

    Integer findSchoolIdBySchoolName(@Param("schoolName" ) String schoolName);


}
