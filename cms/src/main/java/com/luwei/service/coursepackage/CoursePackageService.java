package com.luwei.service.coursepackage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageCmsVO;
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
     * 私有方法 将实体类转为对应的CmsVO类
     *
     * @param coursePackage
     * @return
     */
    private CoursePackageCmsVO toCoursePackageCmsVO(CoursePackage coursePackage) {
        CoursePackageCmsVO cmsVO = new CoursePackageCmsVO();
        BeanUtils.copyProperties(coursePackage, cmsVO);
        return cmsVO;
    }

    /**
     * 根据课程id获取对应的套餐
     *
     * @param courseId
     * @return
     */
    public List<CoursePackageCmsVO> listCmsVO(Integer courseId) {

        return list(new QueryWrapper<CoursePackage>().lambda()
                .eq(CoursePackage::getCourseId, courseId))
                .stream().map(this::toCoursePackageCmsVO).collect(Collectors.toList());
    }

    public BigDecimal findMinPriceByCourseId(Integer courseId) {
        return baseMapper.findMinPriceByCourseId(courseId);
    }

    public int coursePackageTimer(){
       return baseMapper.coursePackageTimer();
    }
}
