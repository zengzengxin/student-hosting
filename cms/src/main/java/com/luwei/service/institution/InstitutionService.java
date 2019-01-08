package com.luwei.service.institution;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.common.util.ValidUtils;
import com.luwei.model.institution.Institution;
import com.luwei.model.institution.InstitutionMapper;
import com.luwei.model.institution.pojo.cms.InstitutionAddDTO;
import com.luwei.model.institution.pojo.cms.InstitutionCmsVO;
import com.luwei.model.institution.pojo.cms.InstitutionQueryDTO;
import com.luwei.model.institution.pojo.cms.InstitutionUpdateDTO;
import com.luwei.model.manager.Manager;
import com.luwei.model.school.School;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.manager.ManagerService;
import com.luwei.service.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2019-01-07
 */
@Service
@Slf4j
public class InstitutionService extends ServiceImpl<InstitutionMapper, Institution> {

    @Resource
    private ManagerService managerService;

    @Resource
    private SchoolService schoolService;

    /**
     * 私有方法 根据id获取实体类,并断言非空,返回
     *
     * @param id
     * @return
     */
    private Institution findById(Integer id) {
        Institution institution = getById(id);
        Assert.notNull(institution, MessageCodes.INSTITUTION_IS_NOT_EXIST);
        return institution;
    }

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param institution
     * @return
     */
    private InstitutionCmsVO toInstitutionCmsVO(Institution institution) {
        InstitutionCmsVO institutionVO = new InstitutionCmsVO();
        BeanUtils.copyProperties(institution, institutionVO);
        return institutionVO;
    }

    /**
     * 新增Institution
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public InstitutionCmsVO saveInstitution(InstitutionAddDTO addDTO) {
        // 手机号校验
        boolean isPhone = ValidUtils.isPhone(addDTO.getLeaderPhone());
        Assert.isTrue(isPhone, MessageCodes.INVALID_PHONE_NUMBER);

        Institution institution = new Institution();
        BeanUtils.copyProperties(addDTO, institution);
        LocalDateTime time = LocalDateTime.now();
        institution.setUpdateTime(time);
        institution.setCreateTime(time);

        // 机构绑定学校
        School school = schoolService.getById(addDTO.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        institution.setSchoolId(school.getSchoolId()).setSchoolName(school.getName());

        Assert.isTrue(save(institution), MessageCodes.INSTITUTION_SAVE_ERROR);
        log.info("保存数据: {}", institution);
        return toInstitutionCmsVO(institution);
    }

    /**
     * 批量删除Institution
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteInstitutions(Set<Integer> ids) {
        int count = baseMapper.deleteBatchIds(ids);
        Assert.isTrue(count == ids.size(), MessageCodes.INSTITUTION_DELETE_ERROR);
        log.info("删除数据: id {}", ids);
    }

    /**
     * 修改Institution
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public InstitutionCmsVO updateInstitution(InstitutionUpdateDTO updateDTO) {
        // 手机号校验
        boolean isPhone = ValidUtils.isPhone(updateDTO.getLeaderPhone());
        Assert.isTrue(isPhone, MessageCodes.INVALID_PHONE_NUMBER);

        Institution institution = new Institution();
        BeanUtils.copyProperties(updateDTO, institution);
        institution.setUpdateTime(LocalDateTime.now());

        // 机构绑定学校
        School school = schoolService.getById(updateDTO.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        institution.setSchoolId(school.getSchoolId()).setSchoolName(school.getName());

        Assert.isTrue(updateById(institution), MessageCodes.INSTITUTION_UPDATE_ERROR);
        log.info("修改数据: bean {}", updateDTO);
        return getInstitution(institution.getInstitutionId());
    }

    /**
     * 获取单个Institution
     *
     * @param id
     * @return
     */
    public InstitutionCmsVO getInstitution(Integer id) {
        return toInstitutionCmsVO(findById(id));
    }

    /**
     * 分页获取Institution
     *
     * @param queryDTO
     * @param page
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IPage<InstitutionCmsVO> findPage(InstitutionQueryDTO queryDTO, Page<Institution> page) {
        LambdaQueryWrapper<Institution> wrapper = new QueryWrapper<Institution>().lambda();

        // 如果学校角色调用此方法, 则只返回此学校的数据
        Integer managerId = UserHelper.getUserId();
        Manager manager = managerService.getById(managerId);
        if (manager.getRole() == RoleEnum.OPERATOR) {
            wrapper.eq(Institution::getSchoolId, manager.getSchoolId());
        }

        if (!StringUtils.isEmpty(queryDTO.getName())) {
            wrapper.like(Institution::getName, queryDTO.getName());
        }
        if (!StringUtils.isEmpty(queryDTO.getLeaderPhone())) {
            wrapper.like(Institution::getLeaderPhone, queryDTO.getLeaderPhone());
        }
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toInstitutionCmsVO);
    }

}
