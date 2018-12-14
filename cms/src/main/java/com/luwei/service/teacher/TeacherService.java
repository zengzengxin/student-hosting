package com.luwei.service.teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.teacher.Teacher;
import com.luwei.model.teacher.TeacherMapper;
import com.luwei.model.teacher.pojo.cms.TeacherAddDTO;
import com.luwei.model.teacher.pojo.cms.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.cms.TeacherUpdateDTO;
import com.luwei.model.teacher.pojo.cms.TeacherVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzx
 * @since 2018-12-13
 */
@Service
@Slf4j
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    public TeacherVO findById(Integer teacherId) {
        Teacher teacher = getById(teacherId);
        //TODO记得修改MessageCodes
        Assert.notNull(teacher, MessageCodes.TEACHER_IS_NOT_EXIST);
        return toTeacherVO(teacher);
    }

    private TeacherVO toTeacherVO(Teacher teacher) {
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyNonNullProperties(teacher, teacherVO);
        return teacherVO;
    }

    @Transactional
    public TeacherVO saveTeacher(TeacherAddDTO teacherAddDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyNonNullProperties(teacherAddDTO, teacher);
        LocalDateTime time = LocalDateTime.now();
        teacher.setUpdateTime(time);
        teacher.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(teacher);
        Assert.isTrue(isSuccess, MessageCodes.TEACHER_SAVE_ERROR);
        log.info("保存数据---:{}", teacher);
        return toTeacherVO(teacher);
    }



    @Transactional
    public void deleteTeachers(Set<Integer> teacherIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(teacherIds);
        Assert.isTrue(count == teacherIds.size(), MessageCodes.TEACHER_DELETE_ERROR);
        log.info("删除数据:ids{}", teacherIds);
    }


    @Transactional
    public TeacherVO updateTeacher(TeacherUpdateDTO teacherUpdateDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyNonNullProperties(teacherUpdateDTO, teacher);

        teacher.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(teacher), MessageCodes.TEACHER_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", teacherUpdateDTO);
        return findById(teacher.getTeacherId());
    }


    public IPage<TeacherVO> findTeacherPage(TeacherQueryDTO teacherQueryDTO, Page page) {
        return baseMapper.getTeacherPage(page,teacherQueryDTO);
    }


    public List<TeacherVO> teacherList(Integer schoolId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Teacher::getSchoolId,schoolId);
        return baseMapper.selectList(queryWrapper).stream().map(this::toTeacherVO).collect(Collectors.toList());
    }

    public List<TeacherVO> findTeacher(Integer schoolId, String teacherName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Teacher::getSchoolId,schoolId).like(Teacher::getTeacherName,teacherName);
        return baseMapper.selectList(queryWrapper).stream().map(this::toTeacherVO).collect(Collectors.toList());
    }
}
