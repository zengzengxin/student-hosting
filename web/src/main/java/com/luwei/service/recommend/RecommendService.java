package com.luwei.service.recommend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.RecommendMapper;
import com.luwei.model.recommend.pojo.web.RecommendWebDTO;
import com.luwei.model.recommend.pojo.web.RecommendWebVO;
import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Service
@Slf4j
public class RecommendService extends ServiceImpl<RecommendMapper, Recommend> {

    @Resource
    private CoursePackageService coursePackageService;

    @Resource
    private RecommendMapper recommendMapper;

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    public Recommend findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Recommend recommend = getById(id);
        Assert.notNull(recommend, MessageCodes.DATA_IS_NOT_EXIST);
        return recommend;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param recommend
     * @return
     */
    private RecommendWebVO toRecommendVO(Recommend recommend) {
        RecommendWebVO recommendVO = new RecommendWebVO();
        BeanUtils.copyProperties(recommend, recommendVO);
        return recommendVO;
    }

    /**
     * 分页获取Recommend
     *
     * @param queryDTO
     * @param page
     * @return
     */
    public IPage<RecommendWebVO> findPage(RecommendWebDTO queryDTO, Page<Recommend> page) {
        // noinspection unchecked
        IPage<RecommendWebVO> iPage = ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Recommend>().lambda()
                .eq(Recommend::getSchoolId, queryDTO.getSchoolId())
                .orderByAsc(Recommend::getWeight)
        ), this::toRecommendVO);

        // 拦截，若课程套餐全部过期/下架，则不显示改课程
        List<RecommendWebVO> list = iPage.getRecords();
        List<RecommendWebVO> resultList = new ArrayList<>();
        for (RecommendWebVO recommendWebVO : list) {
            CoursePackage one = coursePackageService.getOne(new QueryWrapper<CoursePackage>()
                    .eq("course_id", recommendWebVO.getServiceId()).eq("display", true).eq("overdue", false));
            if (one != null) {
                resultList.add(recommendWebVO);
            }
        }

        return iPage.setRecords(resultList);
    }

    public List<RecommendWebVO> findPages() {
        List<Recommend> recommendList = recommendMapper.selectList(new QueryWrapper<>());
        List<RecommendWebVO> recommendWebVOList = new ArrayList<RecommendWebVO>();
        for (Recommend recommend : recommendList) {
            RecommendWebVO recommendWebVO = new RecommendWebVO();
            BeanUtils.copyProperties(recommend, recommendWebVO);
            recommendWebVOList.add(recommendWebVO);
        }
        return recommendWebVOList;
    }

    public Recommend findBySeverId(Integer id) {
        QueryWrapper<Recommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Recommend::getServiceId,id);
        return  recommendMapper.selectOne(queryWrapper);
    }
}
