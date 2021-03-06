package com.luwei.service.banner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.BannerMapper;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerCmsVO;
import com.luwei.model.banner.pojo.cms.BannerQueryDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.course.Course;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import com.luwei.model.school.School;
import com.luwei.model.search.SearchCmsVO;
import com.luwei.service.course.CourseService;
import com.luwei.service.hosting.HostingService;
import com.luwei.service.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    @Resource
    private CourseService courseService;

    @Resource
    private HostingService hostingService;

    @Resource
    private SchoolService schoolService;

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    public Banner findById(Integer id) {
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
    private BannerCmsVO toBannerVO(Banner banner) {
        BannerCmsVO bannerVO = new BannerCmsVO();
        BeanUtils.copyProperties(banner, bannerVO);
        return bannerVO;
    }

    /**
     * 新增Banner
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BannerCmsVO saveBanner(BannerAddDTO addDTO) {
        // 学校ID和学校名称
        School school = schoolService.getById(addDTO.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);

        Banner banner = new Banner();
        BeanUtils.copyProperties(addDTO, banner);
        LocalDateTime time = LocalDateTime.now();
        banner.setSchoolId(school.getSchoolId())
                .setSchoolName(school.getName())
                .setCreateTime(time)
                .setUpdateTime(time);
        Assert.isTrue(save(banner), MessageCodes.BANNER_SAVE_ERROR);
        log.info("保存数据: {}", banner);
        return toBannerVO(banner);
    }

    /**
     * 批量删除Banner
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public BannerCmsVO updateBanner(BannerUpdateDTO updateDTO) {
        // 学校ID和学校名称
        School school = schoolService.getById(updateDTO.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);

        Banner banner = new Banner();
        BeanUtils.copyProperties(updateDTO, banner);
        banner.setUpdateTime(LocalDateTime.now());
        banner.setSchoolId(school.getSchoolId())
                .setSchoolName(school.getName());
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
    public BannerCmsVO getBanner(Integer id) {
        return toBannerVO(findById(id));
    }

    /**
     * 分页获取Banner
     *
     * @param queryDTO
     * @param page
     * @return
     */
    public IPage<BannerCmsVO> findPage(BannerQueryDTO queryDTO, Page<Banner> page) {
        // ObjectUtils.isEmpty(queryDTO.getBannerType());
        return ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Banner>().lambda()
                .eq(Banner::getBannerType, queryDTO.getBannerType())
        ), this::toBannerVO);
    }

    public List<SearchCmsVO> listCourses() {
        // 后续可优化为只查需要的字段
        List<SearchCmsVO> list = new ArrayList<>();
        List<Course> courseList = courseService.list(null);
        for (Course course : courseList) {
            SearchCmsVO cmsVO = new SearchCmsVO();
            cmsVO.setServiceId(course.getCourseId());
            cmsVO.setServiceName(course.getCourseName());
            cmsVO.setServiceType(ServiceTypeEnum.COURSE);
            list.add(cmsVO);
        }
        return list;
    }

    public List<SearchCmsVO> listHosting() {
        // 后续可优化为只查需要的字段
        List<SearchCmsVO> list = new ArrayList<>();
        List<Hosting> hostingList = hostingService.list(null);
        for (Hosting hosting : hostingList) {
            SearchCmsVO cmsVO = new SearchCmsVO();
            cmsVO.setServiceId(hosting.getHostingId());
            cmsVO.setServiceName(hosting.getName());
            cmsVO.setServiceType(ServiceTypeEnum.HOSTING);
            list.add(cmsVO);
        }
        return list;
    }
}
