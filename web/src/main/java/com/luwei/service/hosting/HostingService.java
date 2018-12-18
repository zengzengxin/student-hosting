package com.luwei.service.hosting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.hosting.HostingMapper;
import com.luwei.model.hosting.pojo.web.HostingQueryDTO;
import com.luwei.model.hosting.pojo.web.HostingVO;
import com.luwei.model.picture.PictureMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzx
 * @since 2018-12-17
 */
@Service
@Slf4j
public class HostingService extends ServiceImpl<HostingMapper, Hosting> {


    @Resource
    private PictureMapper pictureMapper;



    public HostingVO findById(Integer hostingId) {
        Hosting hosting = getById(hostingId);
        //TODO记得修改MessageCodes
        Assert.notNull(hosting, MessageCodes.HOSTING_IS_NOT_EXIST);
        //设置图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(hosting.getHostingId());
        return toHostingVO(hosting).setPictureUrls(urls);
    }

    private HostingVO toHostingVO(Hosting hosting) {
        HostingVO hostingVO = new HostingVO();
        BeanUtils.copyNonNullProperties(hosting, hostingVO);
        return hostingVO;
    }




    public IPage<HostingVO> findHostingPage(HostingQueryDTO hostingQueryDTO, Page page) {
        Hosting hosting = new Hosting();
        QueryWrapper<Hosting> wrapper = new QueryWrapper<>(hosting);
        IPage<HostingVO> iPage = ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toHostingVO);
        List<HostingVO> list = iPage.getRecords();
        List collect = list.stream().map(this::dealWith).collect(Collectors.toList());

        return iPage;
    }

    private HostingVO dealWith(HostingVO hostingVO) {
        // 设置图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(hostingVO.getHostingId());

        return hostingVO.setPictureUrls(urls);
    }



}
