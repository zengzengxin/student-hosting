package com.luwei.model.recommend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
public interface RecommendMapper extends BaseMapper<Recommend> {

    Integer realDeleteByServiceIdAndServiceType(@Param("serviceId") Integer serviceId, @Param("serviceType") Integer serviceType);
}
