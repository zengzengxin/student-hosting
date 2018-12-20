package com.luwei.service.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
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
    public MyCourseVO getMyCourse(Integer id) {
        log.info("---------------------------------------------------------");
        CoursePackage coursePackage = coursePackageService.getById(id);
        MyCourseVO myCourseVO = new MyCourseVO();
        BeanUtils.copyProperties(coursePackage, myCourseVO);
        return myCourseVO;
    }

    public List<MyCourseVO> listMyCourse(MyCourseQuery query) {

        QueryWrapper<CoursePackage> wrapper = new QueryWrapper<>();
        wrapper.ge("start_time", query.getStartTime())
                .le("start_time", query.getEndTime())
                .or()
                .ge("end_time", query.getStartTime())
                .lt("end_time", query.getEndTime());

        /*
         SELECT * FROM tb_course_package WHERE course_package_id NOT IN
         (SELECT * FROM tb_course_package
         WHERE (start_time >= '2018-12-05 15:57:10'
         OR end_time <= '2018-12-01 15:57:10')
         AND teacher_id = 1)
         AND teacher_id = 1
         */

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
