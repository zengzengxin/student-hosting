package com.luwei.service.teacher;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.teacher.Teacher;
import com.luwei.model.teacher.TeacherMapper;
import com.luwei.model.teacher.pojo.cms.TeacherVO;
import com.luwei.model.teacher.pojo.web.TeacherUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Service
@Slf4j
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Teacher findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Teacher teacher = getById(id);
        // TODO 修改MessageCodes
        Assert.notNull(teacher, MessageCodes.TEACHER_IS_NOT_EXIST);
        return teacher;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param teacher
     * @return
     */
    private TeacherVO toTeacherVO(Teacher teacher) {
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(teacher, teacherVO);
        return teacherVO;
    }

    /**
     * 修改Teacher
     *
     * @param updateDTO
     * @return
     */
    @Transactional
    public TeacherVO updateTeacher(TeacherUpdateDTO updateDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(updateDTO, teacher);
        teacher.setUpdateTime(LocalDateTime.now());
        // updateById不会把null的值赋值,修改成功后也不会赋值数据库所有的字段
        Assert.isTrue(updateById(teacher), MessageCodes.TEACHER_UPDATE_ERROR);
        log.info("修改数据: bean {}", updateDTO);
        return getTeacher(teacher.getTeacherId());
    }

    /**
     * 获取单个Teacher
     *
     * @param id
     * @return
     */
    public TeacherVO getTeacher(Integer id) {
        return toTeacherVO(findById(id));
    }

}
