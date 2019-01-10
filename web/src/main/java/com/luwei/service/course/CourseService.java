package com.luwei.service.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.course.pojo.web.CourseQuery;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.course.pojo.web.SimpleCourseVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.pojo.web.CoursePackageWebVO;
import com.luwei.model.picture.PictureMapper;
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.model.school.School;
import com.luwei.service.coursepackage.CoursePackageService;
import com.luwei.service.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Resource
    private CoursePackageService coursePackageService;

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private SchoolService schoolService;

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
     * 转为SimpleCourseVO返回,只在公众号课程分页查看时使用
     *
     * @param course
     * @return
     */
    private SimpleCourseVO toSimpleCourseVO(Course course) {
        SimpleCourseVO courseVO = new SimpleCourseVO();
        BeanUtils.copyProperties(course, courseVO);
        return courseVO;
    }

    /**
     * 转为CourseWebVO返回
     *
     * @param course
     * @return
     */
    private CourseWebVO toCourseWebVO(Course course) {
        CourseWebVO courseWebVO = new CourseWebVO();
        BeanUtils.copyProperties(course, courseWebVO);
        return courseWebVO;
    }

    private CoursePackageWebVO toCoursePackageVO(CoursePackage coursePackage) {
        CoursePackageWebVO packageVO = new CoursePackageWebVO();
        BeanUtils.copyProperties(coursePackage, packageVO);
        return packageVO;
    }

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    public CourseWebVO getCourse(Integer id) {
        Course course = findById(id);
        CourseWebVO courseWebVO = toCourseWebVO(course);

        // 封装图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(course.getCourseId(), PictureTypeEnum.COURSE.getValue());

        // 封装课程
        List<CoursePackageWebVO> list = coursePackageService.listWebVO(course.getCourseId());

        // 负责人电话
        School school = schoolService.getById(course.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);

        courseWebVO.setCoursePackageList(list).setPictureUrls(urls).setLeaderPhone(school.getLeaderPhone());
        return courseWebVO;
    }

    /**
     * 分页获取Course
     *
     * @param queryDTO
     * @param page
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IPage<SimpleCourseVO> findPage(CourseQuery queryDTO, Page<Course> page) {

        // 分页查
        IPage<SimpleCourseVO> iPage = ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Course>().lambda()
                .eq(Course::getSchoolId, queryDTO.getSchoolId()).eq(Course::getDisplay, true)
        ), this::toSimpleCourseVO);

        // 设置最低价格
        List<SimpleCourseVO> collect = iPage.getRecords().stream().map(this::setMinPrice).collect(Collectors.toList());

        return iPage.setRecords(collect);
    }

    private SimpleCourseVO setMinPrice(SimpleCourseVO simpleCourseVO) {
        BigDecimal minPrice = coursePackageService.findMinPriceByCourseId(simpleCourseVO.getCourseId());
        return simpleCourseVO.setPrice(minPrice);
    }

    /*
     * 返回所有的课程
     */
    public List<Course> findList(String name) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (name != null || "".equals(name)) {
            queryWrapper.lambda().like(Course::getCourseName, name);
        }
        return baseMapper.selectList(queryWrapper);

    }

}
