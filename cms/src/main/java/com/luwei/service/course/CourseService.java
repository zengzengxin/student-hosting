package com.luwei.service.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.cms.CourseAddDTO;
import com.luwei.model.course.pojo.cms.CourseQueryDTO;
import com.luwei.model.course.pojo.cms.CourseUpdateDTO;
import com.luwei.model.course.pojo.cms.CourseVO;
import com.luwei.utils.ConversionBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Course findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Course course = getById(id);
        // TODO 修改MessageCodes
        Assert.notNull(course, MessageCodes.DATA_IS_NOT_EXIST);
        return course;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param course
     * @return
     */
    private CourseVO toCourseVO(Course course) {
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(course, courseVO);
        return courseVO;
    }

    /**
     * 新增Course
     *
     * @param addDTO
     * @return
     */
    @Transactional
    public CourseVO saveCourse(CourseAddDTO addDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(addDTO, course);
        LocalDateTime time = LocalDateTime.now();
        course.setUpdateTime(time);
        course.setCreateTime(time);
        Assert.isTrue(save(course), MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据: {}", course);
        return toCourseVO(course);
    }

    /**
     * 批量删除Course
     *
     * @param ids
     */
    @Transactional
    public void deleteCourses(Set<Integer> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 修改Course
     *
     * @param updateDTO
     * @return
     */
    @Transactional
    public CourseVO updateCourse(CourseUpdateDTO updateDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(updateDTO, course);
        course.setUpdateTime(LocalDateTime.now());
        // updateById不会把null的值赋值,修改成功后也不会赋值数据库所有的字段
        Assert.isTrue(updateById(course), MessageCodes.DATA_UPDATE_ERROR);
        log.info("修改数据: bean {}", updateDTO);
        return getCourse(course.getCourseId());
    }

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    public CourseVO getCourse(Integer id) {
        return toCourseVO(findById(id));
    }

    /**
     * 分页获取Course
     *
     * @param queryDTO
     * @param page
     * @return
     */
    public IPage<CourseVO> findPage(CourseQueryDTO queryDTO, Page<Course> page) {
        Course course = new Course();
        QueryWrapper<Course> wrapper = new QueryWrapper<>(course);
        // TODO wrapper根据实际业务封装条件
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toCourseVO);
    }

}
