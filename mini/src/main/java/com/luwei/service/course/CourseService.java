package com.luwei.service.course;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.mini.MyCourseQuery;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageVO;
import com.luwei.model.picture.PictureMapper;
import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class CourseService extends ServiceImpl<CourseMapper, Course> {

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
        List<MyCourseVO> list = coursePackageService.findAllByTime(query.getStartTime(), query.getEndTime());
        return list.stream().map(this::dellWith).collect(Collectors.toList());
    }

    private MyCourseVO dellWith(MyCourseVO myCourseVO) {
        List<Integer> list = new ArrayList<>();

        LocalDateTime startTime = myCourseVO.getStartTime();
        LocalDateTime endTime = myCourseVO.getEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // String start = startTime.format(formatter);
        // String end = endTime.format(formatter);
        LocalDate start = startTime.toLocalDate();
        LocalDate end = endTime.toLocalDate();

        List<String> betweenDate = getBetweenDate(start, end);

        return myCourseVO.setDays(betweenDate);
    }

    private List<String> getBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<String> list = new ArrayList<>();
        // LocalDate startDate = LocalDate.parse(start);
        // LocalDate endDate = LocalDate.parse(end);


        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            if (f.getDayOfWeek() != DayOfWeek.SATURDAY && f.getDayOfWeek() != DayOfWeek.SUNDAY) {
                list.add(f.toString());
            }
        });

        // Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.toString()));
        return list;
    }

    /*public static void main(String[] args) {
        String start = "2018-12-01";
        String end = "2018-12-31";

        // Instant instant = Instant.ofEpochMilli(1546226223000L);
        // ZoneId zone = ZoneId.systemDefault();
        // LocalDateTime end = LocalDateTime.ofInstant(instant, zone);

        // LocalDateTime start = LocalDateTime.now();
        // LocalDateTime start = LocalDateTime.now();

        LocalDate startDate = LocalDate.now();
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();
        System.out.println(dayOfWeek);
        System.out.println(dayOfWeek.getValue());

        System.out.println(LocalDateTime.now());
        System.out.println(LocalDate.now());

        *//*List<String> betweenDate = getBetweenDate(start, end);
        System.out.println(betweenDate);
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.toString());
        });*//*
    }*/

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
