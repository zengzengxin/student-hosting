package com.luwei.service.course;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.course.Course;
import com.luwei.model.course.CourseMapper;
import com.luwei.model.course.pojo.mini.MyCourseQuery;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private CoursePackageService coursePackageService;

    /**
     * 获取单个Course
     *
     * @param id
     * @return
     */
    public MyCourseVO getMyCourse(Integer id) {
        CoursePackage coursePackage = coursePackageService.getById(id);
        MyCourseVO myCourseVO = new MyCourseVO();
        BeanUtils.copyProperties(coursePackage, myCourseVO);
        return myCourseVO;
    }

    public List<MyCourseVO> listMyCourse(MyCourseQuery query) {
        List<MyCourseVO> list = coursePackageService.findAllByTime(query.getStartTime(), query.getEndTime());
        return list.stream().map(this::dealWith).collect(Collectors.toList());
    }

    /**
     * 私有方法: 前端需要进行日历形式显示, 先将数据处理封装days字段
     *
     * @param myCourseVO
     * @return
     */
    private MyCourseVO dealWith(MyCourseVO myCourseVO) {
        List<Integer> list = new ArrayList<>();

        LocalDate start = myCourseVO.getStartTime().toLocalDate();
        LocalDate end = myCourseVO.getEndTime().toLocalDate();

        List<String> betweenDate = getBetweenDate(start, end);
        return myCourseVO.setDays(betweenDate);
    }

    /**
     * 获取两个(LocalDate类型)时间段间的 日期集合(yyyy-MM-dd), 去除周六周日
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private List<String> getBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<String> list = new ArrayList<>();

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 0) {
            return list;
        }
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> {
            // 不计算周六日
            if (f.getDayOfWeek() != DayOfWeek.SATURDAY && f.getDayOfWeek() != DayOfWeek.SUNDAY) {
                list.add(f.toString());
            }
        });
        return list;
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
