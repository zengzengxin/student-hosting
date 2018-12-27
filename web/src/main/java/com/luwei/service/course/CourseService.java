package com.luwei.service.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.cms.CourseCmsVO;
import com.luwei.model.course.pojo.cms.CourseQueryDTO;
import com.luwei.model.course.pojo.web.CourseQuery;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageCmsVO;
import com.luwei.model.picture.PictureMapper;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
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

    private CoursePackageCmsVO toCoursePackageVO(CoursePackage coursePackage) {
        CoursePackageCmsVO packageVO = new CoursePackageCmsVO();
        BeanUtils.copyProperties(coursePackage, packageVO);
        return packageVO;
    }

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    public CourseCmsVO getCourse(Integer id) {
        log.info("---------------------------------------------------------");
        Course course = findById(id);
        CourseCmsVO courseVO = new CourseCmsVO();
        BeanUtils.copyProperties(course, courseVO);

        // 封装图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(id, 0);

        // 封装课程
        List<CoursePackageCmsVO> list = coursePackageMapper.findAllByCourseId(id);

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
    @Transactional(rollbackFor = Exception.class)
    public IPage<CourseWebVO> findPage(CourseQuery queryDTO, Page<Course> page) {

        // 分页查
        IPage<CourseWebVO> iPage = ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Course>().lambda()
                .eq(Course::getSchoolId, queryDTO.getSchoolId())
        ), this::toCourseWebVO);

        // 设置最低价格
        List<CourseWebVO> collect = iPage.getRecords().stream().map(this::dealWith2).collect(Collectors.toList());

        return iPage.setRecords(collect);
    }

    private CourseWebVO dealWith2(CourseWebVO courseWebVO) {
        BigDecimal minPrice = coursePackageMapper.findMinPriceByCourseId(courseWebVO.getCourseId());
        return courseWebVO.setPrice(minPrice);
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
