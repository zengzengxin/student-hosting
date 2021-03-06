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
import com.luwei.model.hosting.pojo.web.HostingQuery;
import com.luwei.model.hosting.pojo.web.HostingWebVO;
import com.luwei.model.picture.PictureMapper;
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.model.school.School;
import com.luwei.model.school.SchoolMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzx
 * @since 2018-12-17
 */
@Service
@Slf4j
public class HostingService extends ServiceImpl<HostingMapper, Hosting> {

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private SchoolMapper schoolMapper;

    public HostingWebVO findById(Integer hostingId) {
        Hosting hosting = getById(hostingId);
        Assert.notNull(hosting, MessageCodes.HOSTING_IS_NOT_EXIST);
        //设置图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(hosting.getHostingId(), PictureTypeEnum.HOSTING.getValue());
        //负责人电话
        School school = schoolMapper.selectById(hosting.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);

        return toHostingVO(hosting).setPictureUrls(urls).setLeaderPhone(school.getLeaderPhone());
    }

    private HostingWebVO toHostingVO(Hosting hosting) {
        HostingWebVO hostingVO = new HostingWebVO();
        BeanUtils.copyNonNullProperties(hosting, hostingVO);
        return hostingVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public IPage<HostingWebVO> findHostingPage(HostingQuery query, Page<Hosting> page) {
        // 分页查
        IPage<HostingWebVO> iPage = ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Hosting>().lambda()
                .eq(Hosting::getSchoolId, query.getSchoolId()).eq(Hosting::getDisplay, true)
                .eq(Hosting::getOverdue, false)
        ), this::toHostingVO);

        List<HostingWebVO> list = iPage.getRecords();
        List collect = list.stream().map(this::dealWith).collect(Collectors.toList());

        return iPage;
    }

    private HostingWebVO dealWith(HostingWebVO hostingVO) {
        // 设置图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(hostingVO.getHostingId(), PictureTypeEnum.HOSTING.getValue());

        return hostingVO.setPictureUrls(urls);
    }

    /**
     * 返回所有托管
     *
     * @param name
     * @return
     */
    public List<Hosting> findList(String name) {
        QueryWrapper<Hosting> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(name)) {
            queryWrapper.lambda().like(Hosting::getName, name);
        }
        return baseMapper.selectList(queryWrapper);

    }

    public List<HostingWebVO> getHostings(int start) {
        List<HostingWebVO> hostingWebVOList = new ArrayList<>();
        List<Hosting> hostings = baseMapper.getHostings(start);
        for (Hosting hosting : hostings) {
            HostingWebVO hostingWebVO = new HostingWebVO();
            BeanUtils.copyNonNullProperties(hosting,hostingWebVO);
            hostingWebVOList.add(hostingWebVO);
        }
        return hostingWebVOList;
    }
}
