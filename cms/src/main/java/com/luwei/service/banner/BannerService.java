package com.luwei.service.banner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.BannerMapper;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerQueryDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.banner.pojo.cms.BannerVO;
import com.luwei.utils.ConversionBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Banner findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Banner banner = getById(id);
        Assert.notNull(banner, MessageCodes.BANNER_IS_NOT_EXIST);
        return banner;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param banner
     * @return
     */
    private BannerVO toBannerVO(Banner banner) {
        BannerVO bannerVO = new BannerVO();
        BeanUtils.copyProperties(banner, bannerVO);
        return bannerVO;
    }

    /**
     * 新增Banner
     *
     * @param addDTO
     * @return
     */
    @Transactional
    public BannerVO saveBanner(BannerAddDTO addDTO) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(addDTO, banner);
        LocalDateTime time = LocalDateTime.now();
        banner.setUpdateTime(time);
        banner.setCreateTime(time);
        Assert.isTrue(save(banner), MessageCodes.BANNER_SAVE_ERROR);
        log.info("保存数据: {}", banner);
        return toBannerVO(banner);
    }

    /**
     * 批量删除Banner
     *
     * @param ids
     */
    @Transactional
    public void deleteBanners(Set<Integer> ids) {
        // 若用removeByIds,因为删除不存在的逻辑上属于成功,所以也返回true
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.BANNER_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 修改Banner
     *
     * @param updateDTO
     * @return
     */
    @Transactional
    public BannerVO updateBanner(BannerUpdateDTO updateDTO) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(updateDTO, banner);
        banner.setUpdateTime(LocalDateTime.now());
        // updateById不会把null的值赋值,修改成功后也不会赋值数据库所有的字段
        Assert.isTrue(updateById(banner), MessageCodes.BANNER_UPDATE_ERROR);
        log.info("修改数据: bean {}", updateDTO);
        return getBanner(banner.getBannerId());
    }

    /**
     * 获取单个Banner
     *
     * @param id
     * @return
     */
    public BannerVO getBanner(Integer id) {
        return toBannerVO(findById(id));
    }

    /**
     * 分页获取Banner
     *
     * @param queryDTO
     * @param page
     * @return
     */
    public IPage<BannerVO> findPage(BannerQueryDTO queryDTO, Page<Banner> page) {
        Banner banner = new Banner();
        QueryWrapper<Banner> wrapper = new QueryWrapper<>(banner);
        wrapper.eq("banner_type", queryDTO.getBannerType().getValue());
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toBannerVO);
    }

}
