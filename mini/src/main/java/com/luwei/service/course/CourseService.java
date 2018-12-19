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
import com.luwei.model.course.pojo.mini.MyCourseQuery;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageVO;
import com.luwei.model.picture.PictureMapper;
import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
    private CoursePackageService coursePackageService;

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

    private CoursePackageVO toCoursePackageVO(CoursePackage coursePackage) {
        CoursePackageVO packageVO = new CoursePackageVO();
        BeanUtils.copyProperties(coursePackage, packageVO);
        return packageVO;
    }

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    /*public MyCourseVO getMyCourse(Integer id) {
        log.info("---------------------------------------------------------");
        Course course = findById(id);
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(course, courseVO);

        courseVO.setCoursePackageList(list).setPictureUrls(urls);
        return courseVO;
    }*/

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

    public List<MyCourseVO> listMyCourse(MyCourseQuery query) {

        QueryWrapper<CoursePackage> wrapper = new QueryWrapper<>();
        wrapper.ge("start_time", query.getStartTime())
                .le("start_time", query.getEndTime())
                .or()
                .ge("end_time", query.getStartTime())
                .lt("end_time", query.getEndTime());

        List<CoursePackage> list = coursePackageService.list(wrapper);
        System.out.println(list);
        return list.stream().map(this::toMyCourseVO).collect(Collectors.toList());
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param coursePackage
     * @return
     */
    private MyCourseVO toMyCourseVO(CoursePackage coursePackage) {
        MyCourseVO myCourseVO = new MyCourseVO();
        BeanUtils.copyProperties(coursePackage, myCourseVO);
        return myCourseVO;
    }

}
