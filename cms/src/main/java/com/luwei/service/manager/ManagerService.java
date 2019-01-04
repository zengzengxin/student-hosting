package com.luwei.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.BcryptUtil;
import com.luwei.model.manager.Manager;
import com.luwei.model.manager.ManagerMapper;
import com.luwei.model.manager.pojo.*;
import com.luwei.model.school.School;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.school.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author jdq
 * @date 2017/12/7 14:52
 */
@Slf4j
@Service
public class ManagerService extends ServiceImpl<ManagerMapper, Manager> {

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private SchoolService schoolService;

    @Value("${luwei.config.salt}")
    private String salt;

    /**
     * 获得管理员列表
     *
     * @param managerQueryVO
     * @param page
     * @return IPage<ManagerPageVO>
     */
    public IPage<ManagerPageVO> findPage(ManagerQueryVO managerQueryVO, Page page) {
        return baseMapper.selectManagerPage(page, managerQueryVO.getManagerId(), managerQueryVO.getName());
    }

    /**
     * Manager -> ManagerPageVO
     *
     * @param manager
     * @return ManagerPageVO
     */
    private ManagerPageVO toManagerPageVO(Manager manager) {
        ManagerPageVO pageVO = new ManagerPageVO();
        BeanUtils.copyProperties(manager, pageVO);
        return pageVO;
    }

    /**
     * 添加管理员
     *
     * @param addVO
     * @return ManagerPageVO
     */
    @Transactional(rollbackFor = Exception.class)
    public ManagerPageVO add(ManagerAddVO addVO, RoleEnum roleEnum) {
        String account = addVO.getAccount();

        String password;
        try {
            password = DigestUtils.md5DigestAsHex((BcryptUtil.decrypt(addVO.getPassword()) + salt).getBytes());
        } catch (Exception e) {
            log.error("RSAUtil.decrypt exception", e);
            throw new IllegalArgumentException(MessageCodes.RSAUtil_DECRYPT_ERROR);
        }
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("account", account);
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.isNull(manager, MessageCodes.ACCOUNT_ALREADY_EXIST);
        manager = new Manager();
        LocalDateTime now = LocalDateTime.now();
        manager.setAccount(account)
                .setName(addVO.getAccount())
                .setRole(roleEnum)
                .setPassword(password)
                .setDisabled(false)
                .setCreateTime(now)
                .setUpdateTime(now);
        School school = schoolService.getById(addVO.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        manager.setSchoolId(school.getSchoolId())
                .setSchoolName(school.getName());
        baseMapper.insert(manager);
        return toManagerPageVO(manager);
    }

    /**
     * 去编辑管理员
     *
     * @param managerId
     * @return ManagerEditVO
     */
    public ManagerEditVO toEdit(int managerId, RoleEnum roleEnum) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("manager_id", managerId).eq("role", roleEnum);
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);

        ManagerEditVO editVO = new ManagerEditVO();
        editVO.setManagerId(managerId);
        editVO.setAccount(manager.getAccount());
        return editVO;
    }

    /**
     * 更新管理员
     *
     * @param editVO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ManagerPageVO update(ManagerEditVO editVO) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>();
        wrapper.lambda().eq(Manager::getManagerId, editVO.getManagerId());
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);

        manager.setAccount(editVO.getAccount());
        // 设置学校id和名称
        School school = schoolService.getById(editVO.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        manager.setSchoolId(school.getSchoolId())
                .setSchoolName(school.getName());
        saveOrUpdate(manager);

        return toManagerPageVO(manager);
    }

    /**
     * 开启或禁用管理员
     *
     * @param managerStateVO
     */
    @Transactional(rollbackFor = Exception.class)
    public ManagerPageVO handleDisabled(ManagerStateVO managerStateVO) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>();
        wrapper.lambda().eq(Manager::getManagerId, managerStateVO.getManagerId());
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        Assert.isTrue(!Objects.equals(manager.getRole(), RoleEnum.ROOT), MessageCodes.ROOT_CANNOT_DISABLED);
        if (manager.getDisabled()) {
            shiroTokenService.logout(managerStateVO.getManagerId().toString());
        }
        baseMapper.update(manager, new UpdateWrapper<Manager>().lambda().set(Manager::getDisabled, !manager.getDisabled()).eq(Manager::getManagerId, manager.getManagerId()));
        return toManagerPageVO(manager);
    }

    /**
     * 删除管理员
     *
     * @param idList
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> idList) {
        Manager manager;
        for (Integer managerId : idList) {
            QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>();
            wrapper.lambda().eq(Manager::getManagerId, managerId);
            manager = baseMapper.selectOne(wrapper);
            Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
            Assert.isTrue(!Objects.equals(manager.getRole(), RoleEnum.ROOT), MessageCodes.ROOT_CANNOT_DELETE);
            shiroTokenService.login(managerId.toString());
            baseMapper.deleteById(managerId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ManagerPageVO resetPassword(ManagerResetPasswordVO managerResetPasswordVO) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("manager_id", managerResetPasswordVO.getManagerId());
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        // 不可在此重置ROOT密码
        if (manager.getRole() == RoleEnum.ROOT) {
            throw new ValidationException(MessageCodes.ROOT_CANNOT_RESET);
        }

        String md5Password;
        try {
            md5Password = DigestUtils.md5DigestAsHex((BcryptUtil.decrypt(managerResetPasswordVO.getPassword()) + salt).getBytes());
        } catch (Exception e) {
            log.error("RSAUtil.decrypt exception", e);
            throw new IllegalArgumentException(MessageCodes.RSAUtil_DECRYPT_ERROR);
        }
        baseMapper.update(new Manager(), new UpdateWrapper<Manager>().lambda().set(Manager::getPassword, md5Password).eq(Manager::getManagerId, manager.getManagerId()));

        return toManagerPageVO(manager);
    }

    @Transactional(rollbackFor = Exception.class)
    public ManagerPageVO bindingSchool(ManagerBindingSchool bindingSchool) {
        Manager manager = getById(bindingSchool.getManagerId());
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        School school = schoolService.getById(bindingSchool.getSchoolId());
        Assert.notNull(school, MessageCodes.SCHOOL_IS_NOT_EXIST);
        manager.setSchoolId(school.getSchoolId())
                .setSchoolName(school.getName());
        Assert.isTrue(save(manager), MessageCodes.MANAGER_BINDING_SCHOOL_ERROR);

        ManagerPageVO managerPageVO = new ManagerPageVO();
        BeanUtils.copyProperties(manager, managerPageVO);
        return managerPageVO;
    }

}
