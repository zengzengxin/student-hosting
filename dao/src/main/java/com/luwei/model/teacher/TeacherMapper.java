package com.luwei.model.teacher;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.teacher.pojo.cms.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.cms.TeacherCmsVO;
import com.luwei.model.teacher.pojo.mini.TeacherMiniVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2018-12-13
 */
public interface TeacherMapper extends BaseMapper<Teacher> {


    IPage<TeacherCmsVO> getTeacherPage(@Param("page" ) Page pag, @Param("teacherQueryDTO" ) TeacherQueryDTO teacherQueryDTO);

    Teacher getTeacherByphone(@Param("phone" ) String phone);

    TeacherMiniVO findteacherById(@Param("id" ) Integer id);
}
