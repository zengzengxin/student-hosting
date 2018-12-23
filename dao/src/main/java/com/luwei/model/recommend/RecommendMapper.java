package com.luwei.model.recommend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
public interface RecommendMapper extends BaseMapper<Recommend> {

    int realDeleteByServiceId(Integer serviceId);
}
