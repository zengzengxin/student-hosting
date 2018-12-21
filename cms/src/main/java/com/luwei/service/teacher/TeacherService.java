package com.luwei.service.teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ReadExcelUtil;
import com.luwei.model.teacher.Teacher;
import com.luwei.model.teacher.TeacherMapper;
import com.luwei.model.teacher.pojo.cms.TeacherAddDTO;
import com.luwei.model.teacher.pojo.cms.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.cms.TeacherUpdateDTO;
import com.luwei.model.teacher.pojo.cms.TeacherCmsVO;
import com.luwei.module.shiro.service.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public TeacherCmsVO findById(Integer teacherId) {
        Teacher teacher = getById(teacherId);
        //TODO记得修改MessageCodes
        Assert.notNull(teacher, MessageCodes.TEACHER_IS_NOT_EXIST);
        return toTeacherVO(teacher);
    }

    private TeacherCmsVO toTeacherVO(Teacher teacher) {
        TeacherCmsVO teacherVO = new TeacherCmsVO();
        BeanUtils.copyNonNullProperties(teacher, teacherVO);
        return teacherVO;
    }

    @Transactional
    public TeacherCmsVO saveTeacher(TeacherAddDTO teacherAddDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyNonNullProperties(teacherAddDTO, teacher);
        LocalDateTime time = LocalDateTime.now();
        teacher.setUpdateTime(time);
        teacher.setCreateTime(time);
        teacher.setSchoolId(UserHelper.getUserId());
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
    public TeacherCmsVO updateTeacher(TeacherUpdateDTO teacherUpdateDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyNonNullProperties(teacherUpdateDTO, teacher);

        teacher.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(teacher), MessageCodes.TEACHER_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", teacherUpdateDTO);
        return findById(teacher.getTeacherId());
    }

    public IPage<TeacherCmsVO> findTeacherPage(TeacherQueryDTO teacherQueryDTO, Page page) {
        return baseMapper.getTeacherPage(page, teacherQueryDTO);
    }

    public List<TeacherCmsVO> teacherList(Integer schoolId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(schoolId != null){
            queryWrapper.lambda().eq(Teacher::getSchoolId, schoolId);
        }
        return baseMapper.selectList(queryWrapper).stream().map(this::toTeacherVO).collect(Collectors.toList());
    }

    public List<TeacherCmsVO> findTeacher(Integer schoolId, String teacherName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(schoolId != null){
            queryWrapper.lambda().eq(Teacher::getSchoolId, schoolId);
        }else if(teacherName != null){
            queryWrapper.lambda().like(Teacher::getTeacherName, teacherName);
        }
        return baseMapper.selectList(queryWrapper).stream().map(this::toTeacherVO).collect(Collectors.toList());
    }

    //导入excle、
    public void importExcel(MultipartFile excelFile) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {

        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
        List<Map<Integer, String>> excelInfo = readExcelUtil.getExcelInfo(excelFile);

        List<Teacher> list = new ArrayList<Teacher>();
        for (Map<Integer, String> map : excelInfo) {
            Teacher teacher = new Teacher();
            teacher.setTeacherName(map.get(0));
            teacher.setPhone(map.get(1));
            teacher.setSchoolName(map.get(2));
            teacher.setGrade(map.get(3));
            teacher.setTeacherClass(map.get(4));
            LocalDateTime time = LocalDateTime.now();
            teacher.setUpdateTime(time);
            teacher.setCreateTime(time);
            list.add(teacher);
        }
        boolean flag = saveBatch(list);
        Assert.isTrue(flag, MessageCodes.TEACHER_IMPORT_FROM_EXCLE_ERROR);
        //--todo-- 需要从redis获得managerId，再由managerId获得学校id，再从学校id获得学校名称存入数据库，这里的学校名称将来要舍弃

    }

}