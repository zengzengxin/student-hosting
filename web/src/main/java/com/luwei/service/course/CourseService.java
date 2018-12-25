package com.luwei.service.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.cms.CourseQueryDTO;
import com.luwei.model.course.pojo.cms.CourseVO;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageAddDTO;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageUpdateDTO;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageVO;
import com.luwei.model.picture.Picture;
import com.luwei.model.picture.PictureMapper;
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Resource
    private CoursePackageMapper coursePackageMapper;

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private SchoolMapper schoolMapper;

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Course findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Course course = getById(id);
        Assert.notNull(course, MessageCodes.COURSE_IS_NOT_EXIST);
        return course;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param course
     * @return
     */
    private CourseWebVO toCourseWebVO(Course course) {
        CourseWebVO courseVO = new CourseWebVO();
        BeanUtils.copyProperties(course, courseVO);
        return courseVO;
    }

    /**
     * 新增Course 并新增课程套餐
     *
     * @param
     * @return
     */
    /*@Transactional
    public CourseWebVO saveCourse(CourseAddDTO addDTO) {
        // 保存课程
        Course course = new Course();
        BeanUtils.copyProperties(addDTO, course);
        LocalDateTime time = LocalDateTime.now();
        course.setUpdateTime(time);
        course.setCreateTime(time);
        Assert.isTrue(save(course), MessageCodes.COURSE_SAVE_ERROR);

        Integer courseId = course.getCourseId();

        // 保存课程套餐
        //List<CoursePackageVO> list = addDTO.getCoursePackageList().stream()
        //        .map(this::saveCoursePackage).collect(Collectors.toList());

        List<CoursePackageAddDTO> temp = addDTO.getCoursePackageList();
        List<CoursePackageVO> list = new ArrayList<>();
        for (CoursePackageAddDTO coursePackageAddDTO : temp) {
            CoursePackageVO packageVO = saveCoursePackage(coursePackageAddDTO, courseId);
            list.add(packageVO);
        }

        // 保存课程图片
        List<String> urls = addDTO.getPictureUrls();
        for (String url : urls) {
            savePicture(url, courseId);
        }

        log.info("保存数据: {}", course);
        return CourseWebVO(course).setPictureUrls(urls).setCoursePackageList(list);
    }*/
    private void savePicture(String url, Integer courseId) {
        Picture picture = new Picture();
        picture.setPictureUrl(url);
        // 图片类型为课程
        picture.setPictureType(PictureTypeEnum.COURSE);
        // 外键ID
        picture.setForeignKeyId(courseId);
        LocalDateTime time = LocalDateTime.now();
        picture.setUpdateTime(time);
        picture.setCreateTime(time);

        int count = pictureMapper.insert(picture);
        Assert.isTrue(count > 0, MessageCodes.COURSE_SAVE_ERROR);
    }

    private CoursePackageVO saveCoursePackage(CoursePackageAddDTO addDTO, Integer courseId) {
        CoursePackage coursePackage = new CoursePackage();
        BeanUtils.copyProperties(addDTO, coursePackage);
        // 课程管理
        coursePackage.setCourseId(courseId);
        LocalDateTime time = LocalDateTime.now();
        coursePackage.setUpdateTime(time);
        coursePackage.setCreateTime(time);

        int count = coursePackageMapper.insert(coursePackage);
        Assert.isTrue(count > 0, MessageCodes.COURSE_SAVE_ERROR);
        return toCoursePackageVO(coursePackage);
    }

    private CoursePackageVO toCoursePackageVO(CoursePackage coursePackage) {
        CoursePackageVO packageVO = new CoursePackageVO();
        BeanUtils.copyProperties(coursePackage, packageVO);
        return packageVO;
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
        Assert.isTrue(count == ids.size(), MessageCodes.COURSE_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 修改Course, 并修改课程套餐
     *
     * @param updateDTO
     * @return
     */
    /*@Transactional
    public CourseWebVO updateCourse(CourseUpdateDTO updateDTO) {
        // 修改课程
        Course course = new Course();
        BeanUtils.copyProperties(updateDTO, course);
        course.setUpdateTime(LocalDateTime.now());
        // updateById不会把null的值赋值,修改成功后也不会赋值数据库所有的字段
        Assert.isTrue(updateById(course), MessageCodes.COURSE_UPDATE_ERROR);

        // 修改课程套餐
        List<CoursePackageVO> list = updateDTO.getCoursePackageList().stream()
                .map(this::updateCoursePackage).collect(Collectors.toList());

        // 修改课程图片--先删除
        pictureMapper.deleteByPictureTypeAndForeignKeyId(PictureTypeEnum.COURSE.getValue(), course.getCourseId());
        // 保存课程图片
        List<String> urls = updateDTO.getPictureUrls();
        Integer courseId = course.getCourseId();
        for (String url : urls) {
            savePicture(url, courseId);
        }
        log.info("修改数据: bean {}", updateDTO);
        return CourseWebVO(course).setPictureUrls(urls).setCoursePackageList(list);
    }*/
    private CoursePackageVO updateCoursePackage(CoursePackageUpdateDTO updateDTO) {
        CoursePackage coursePackage = new CoursePackage();
        BeanUtils.copyProperties(updateDTO, coursePackage);
        coursePackage.setUpdateTime(LocalDateTime.now());
        int count = coursePackageMapper.updateById(coursePackage);
        Assert.isTrue(count > 0, MessageCodes.COURSE_UPDATE_ERROR);
        // 套餐是否过期
        return toCoursePackageVO(coursePackageMapper.selectById(coursePackage.getCoursePackageId()));
    }

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    public CourseVO getCourse(Integer id) {
        log.info("---------------------------------------------------------");
        Course course = findById(id);
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(course, courseVO);

        // 封装图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(id, 0);

        // 封装课程
        List<CoursePackageVO> list = coursePackageMapper.findAllByCourseId(id);

        // 负责人电话
        School school = schoolMapper.selectById(course.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);

        courseVO.setCoursePackageList(list).setPictureUrls(urls).setLeaderPhone(school.getLeaderPhone());
        return courseVO;
    }

    /**
     * 分页获取Course
     *
     * @param queryDTO
     * @param page
     * @return
     */
    @Transactional
    public IPage<CourseWebVO> findPage(CourseQueryDTO queryDTO, Page<Course> page) {
        // 封装条件
        Course course = new Course();
        QueryWrapper<Course> wrapper = new QueryWrapper<>(course);
        if (queryDTO.getCourseName() != null && !queryDTO.getCourseName().equals("")) {
            wrapper.like("course_name", queryDTO.getCourseName());
        }
        wrapper.eq("school_id", queryDTO.getSchoolId());

        // 分页查
        IPage<CourseWebVO> iPage = ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toCourseWebVO);

        // 设置最低价格
        List<CourseWebVO> collect = iPage.getRecords().stream().map(this::dealWith2).collect(Collectors.toList());

        //List<CourseWebVO> list = iPage.getRecords();
        //List collect = list.stream().map(this::dealWith).collect(Collectors.toList());

        return iPage.setRecords(collect);
    }

    private CourseWebVO dealWith2(CourseWebVO courseWebVO) {
        BigDecimal minPrice = coursePackageMapper.findMinPriceByCourseId(courseWebVO.getCourseId());
        return courseWebVO.setPrice(minPrice);
    }

    /*private CourseWebVO dealWith(CourseWebVO courseVO) {
        // 设置图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(courseVO.getCourseId());

        // 设置课程套餐列表
        List<CoursePackageVO> list = coursePackageMapper.findAllByCourseId(courseVO.getCourseId());

        return courseVO.setCoursePackageList(list).setPictureUrls(urls);
    }*/


    /*
     * 返回所有的课程
     * */

    public List<Course> findList(String name) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (name != null || "".equals(name)){
            queryWrapper.lambda().eq(Course::getCourseName, name);
        }
        return baseMapper.selectList(queryWrapper);

    }

}
