package com.luwei.service.hostingPackage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.hostingPackage.HostingPackage;
import com.luwei.model.hostingPackage.HostingPackageMapper;
import com.luwei.model.hostingPackage.pojo.cms.HostingPackageAddDTO;
import com.luwei.model.hostingPackage.pojo.cms.HostingPackageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class HostingPackageService extends ServiceImpl<HostingPackageMapper, HostingPackage> {

   /* public HostingPackageVO findById(Integer hostingpackageId) {
        HostingPackage hostingpackage = getById(hostingpackageId);
        //TODO记得修改MessageCodes
        Assert.notNull(hostingpackage, MessageCodes.DATA_IS_NOT_EXIST);
        return toHostingPackageVO(hostingpackage);
    }



    private HostingPackageVO toHostingPackageVO(HostingPackage hostingpackage) {
        HostingPackageVO hostingpackageVO = new HostingPackageVO();
        BeanUtils.copyNonNullProperties(hostingpackage, hostingpackageVO);
        return hostingpackageVO;
    }

    @Transactional
    public HostingPackageVO saveHostingPackage(HostingPackageAddDTO hostingpackageAddDTO) {
        HostingPackage hostingpackage = new HostingPackage();
        BeanUtils.copyNonNullProperties(hostingpackageAddDTO, hostingpackage);
        LocalDateTime time = LocalDateTime.now();
        hostingpackage.setUpdateTime(time);
        hostingpackage.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(hostingpackage);
        Assert.isTrue(isSuccess, MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据---:{}", hostingpackage);
        return toHostingPackageVO(hostingpackage);
    }

    @Transactional
    public void deleteHostingPackages(Set<Integer> hostingpackageIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(hostingpackageIds);
        Assert.isTrue(count == hostingpackageIds.size(), MessageCodes.DATA_DELETE_ERROR);
        log.info("删除数据:ids{}", hostingpackageIds);
    }

    @Transactional
    public HostingPackageVO updateHostingPackage(HostingPackageUpdateDTO hostingpackageUpdateDTO) {
        HostingPackage hostingpackage = new HostingPackage();
        BeanUtils.copyNonNullProperties(hostingpackageUpdateDTO, hostingpackage);

        hostingpackage.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(hostingpackage), MessageCodes.DATA_IS_UPDATE_ERROR);
        log.info("修改数据：bean:{}", hostingpackageUpdateDTO);
        return findById(hostingpackage.getHostingPackageId());
    }

    public IPage<HostingPackageVO> findHostingPackagePage(HostingPackageQueryDTO hostingpackageQueryDTO, Page page) {
        HostingPackage hostingpackage = new HostingPackage();
        BeanUtils.copyNonNullProperties(hostingpackageQueryDTO, hostingpackage);
        QueryWrapper<HostingPackage> queryWrapper = new QueryWrapper<>();
        //查询业务
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, queryWrapper), this::toHostingPackageVO);
    }*/

    public List<HostingPackageVO> findByHostingId(Integer hostingId) {
        QueryWrapper<HostingPackage> hostingPackageVOQueryWrapper = new QueryWrapper<>();
        hostingPackageVOQueryWrapper.lambda().eq(HostingPackage::getHostingId, hostingId);
        List<HostingPackage> list = list(hostingPackageVOQueryWrapper);
        //TODO记得修改MessageCodes
        Assert.notNull(list, MessageCodes.DATA_IS_NOT_EXIST);
        return list.stream().map(this::toHostingPackageVO).collect(Collectors.toList());
    }

    private HostingPackageVO toHostingPackageVO(HostingPackage hostingpackage) {
        HostingPackageVO hostingpackageVO = new HostingPackageVO();
        BeanUtils.copyNonNullProperties(hostingpackage, hostingpackageVO);
        return hostingpackageVO;
    }


    @Transactional
    public HostingPackageVO saveHostingPackage(HostingPackageAddDTO hostingpackageAddDTO) {
        HostingPackage hostingpackage = new HostingPackage();
        BeanUtils.copyNonNullProperties(hostingpackageAddDTO, hostingpackage);
        LocalDateTime time = LocalDateTime.now();
        hostingpackage.setUpdateTime(time);
        hostingpackage.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(hostingpackage);
        Assert.isTrue(isSuccess, MessageCodes.DATA_SAVE_ERROR);
        log.info("保存数据---:{}", hostingpackage);
        return toHostingPackageVO(hostingpackage);
    }

}
