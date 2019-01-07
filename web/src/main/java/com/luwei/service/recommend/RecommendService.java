package com.luwei.service.recommend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.RecommendMapper;
import com.luwei.model.recommend.pojo.web.RecommendWebVO;
import com.luwei.model.recommend.pojo.web.RecommendWebDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Service
@Slf4j
public class RecommendService extends ServiceImpl<RecommendMapper, Recommend> {

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Recommend findById(Integer id) {
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
        return ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Recommend>().lambda()
                .eq(Recommend::getSchoolId, queryDTO.getSchoolId())
                .orderByAsc(Recommend::getWeight)
        ), this::toRecommendVO);
    }

}
