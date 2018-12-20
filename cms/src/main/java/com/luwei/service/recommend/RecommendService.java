package com.luwei.service.recommend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.RecommendMapper;
import com.luwei.model.recommend.pojo.cms.RecommendAddDTO;
import com.luwei.model.recommend.pojo.cms.RecommendQueryDTO;
import com.luwei.model.recommend.pojo.cms.RecommendUpdateDTO;
import com.luwei.model.recommend.pojo.cms.RecommendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

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
        // TODO 修改MessageCodes
        Assert.notNull(recommend, MessageCodes.DATA_IS_NOT_EXIST);
        return recommend;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param recommend
     * @return
     */
    private RecommendVO toRecommendVO(Recommend recommend) {
        RecommendVO recommendVO = new RecommendVO();
        BeanUtils.copyProperties(recommend, recommendVO);
        return recommendVO;
    }

    /**
     * 新增Recommend
     *
     * @param addDTO
     * @return
     */
    @Transactional
    public RecommendVO saveRecommend(RecommendAddDTO addDTO) {
        Recommend recommend = new Recommend();
        BeanUtils.copyProperties(addDTO, recommend);
        LocalDateTime time = LocalDateTime.now();
        recommend.setUpdateTime(time);
        recommend.setCreateTime(time);
        Assert.isTrue(save(recommend), MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据: {}", recommend);
        return toRecommendVO(recommend);
    }

    /**
     * 批量删除Recommend
     *
     * @param ids
     */
    @Transactional
    public void deleteRecommends(Set<Integer> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 修改Recommend
     *
     * @param updateDTO
     * @return
     */
    @Transactional
    public RecommendVO updateRecommend(RecommendUpdateDTO updateDTO) {
        Recommend recommend = new Recommend();
        BeanUtils.copyProperties(updateDTO, recommend);
        recommend.setUpdateTime(LocalDateTime.now());
        // updateById不会把null的值赋值,修改成功后也不会赋值数据库所有的字段
        Assert.isTrue(updateById(recommend), MessageCodes.DATA_UPDATE_ERROR);
        log.info("修改数据: bean {}", updateDTO);
        return getRecommend(recommend.getRecommendId());
    }

    /**
     * 获取单个Recommend
     *
     * @param id
     * @return
     */
    public RecommendVO getRecommend(Integer id) {
        return toRecommendVO(findById(id));
    }

    /**
     * 分页获取Recommend
     *
     * @param queryDTO
     * @param page
     * @return
     */
    public IPage<RecommendVO> findPage(RecommendQueryDTO queryDTO, Page<Recommend> page) {
        Recommend recommend = new Recommend();
        QueryWrapper<Recommend> wrapper = new QueryWrapper<>(recommend);
        // TODO wrapper根据实际业务封装条件
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toRecommendVO);
    }

}
