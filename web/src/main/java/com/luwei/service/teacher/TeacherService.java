package com.luwei.service.teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.teacher.Teacher;
import com.luwei.model.teacher.TeacherMapper;
import com.luwei.model.teacher.pojo.cms.TeacherVO;
import com.luwei.model.teacher.pojo.web.TeacherAddDTO;
import com.luwei.model.teacher.pojo.web.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.web.TeacherUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-18
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
        Assert.notNull(teacher, MessageCodes.DATA_IS_NOT_EXIST);
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
     * 新增Teacher
     *
     * @param addDTO
     * @return
     */
    @Transactional
    public TeacherVO saveTeacher(TeacherAddDTO addDTO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(addDTO, teacher);
        LocalDateTime time = LocalDateTime.now();
        teacher.setUpdateTime(time);
        teacher.setCreateTime(time);
        Assert.isTrue(save(teacher), MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据: {}", teacher);
        return toTeacherVO(teacher);
    }

    /**
     * 批量删除Teacher
     *
     * @param ids
     */
    @Transactional
    public void deleteTeachers(Set<Integer> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
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
        Assert.isTrue(updateById(teacher), MessageCodes.DATA_UPDATE_ERROR);
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

    /**
     * 分页获取Teacher
     *
     * @param queryDTO
     * @param page
     * @return
     */
    public IPage<TeacherVO> findPage(TeacherQueryDTO queryDTO, Page<Teacher> page) {
        Teacher teacher = new Teacher();
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>(teacher);
        // TODO wrapper根据实际业务封装条件
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toTeacherVO);
    }

    public IPage<TeacherVO> courseList(@Valid TeacherQueryDTO queryDTO) {

        return null;
    }
}
