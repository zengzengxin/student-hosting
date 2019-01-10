package com.luwei.service.coursepackage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.model.coursepackage.pojo.web.CoursePackageWebVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class CoursePackageService extends ServiceImpl<CoursePackageMapper, CoursePackage> {

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    public CoursePackage findById(Integer id) {
        CoursePackage coursePackage = getById(id);
        Assert.notNull(coursePackage, MessageCodes.BANNER_IS_NOT_EXIST);
        return coursePackage;
    }

    /**
     * 私有方法 将实体类转为对应的WebVO类
     *
     * @param coursePackage
     * @return
     */
    private CoursePackageWebVO toCoursePackageWebVO(CoursePackage coursePackage) {
        CoursePackageWebVO webVO = new CoursePackageWebVO();
        BeanUtils.copyProperties(coursePackage, webVO);
        return webVO;
    }

    /**
     * 根据课程id获取对应的套餐
     *
     * @param courseId
     * @return
     */
    public List<CoursePackageWebVO> listWebVO(Integer courseId) {

        List<CoursePackage> list = list(new QueryWrapper<CoursePackage>().lambda()
                .eq(CoursePackage::getOverdue, false)
                .eq(CoursePackage::getDisplay, true)
                .eq(CoursePackage::getCourseId, courseId));
        return list.stream().map(this::toCoursePackageWebVO).collect(Collectors.toList());
    }

    public BigDecimal findMinPriceByCourseId(Integer courseId) {
        return baseMapper.findMinPriceByCourseId(courseId);
    }

}
