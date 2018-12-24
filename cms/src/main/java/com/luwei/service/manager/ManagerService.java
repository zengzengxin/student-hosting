package com.luwei.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BcryptUtil;
import com.luwei.model.manager.Manager;
import com.luwei.model.manager.ManagerMapper;
import com.luwei.model.manager.pojo.*;
import com.luwei.module.shiro.service.ShiroTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
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

    @Value("${luwei.config.salt}")
    private String salt;

    /**
     * 获得管理员列表
     *
     * @param managerQueryVO
     * @param page
     * @return IPage<ManagerPageVO>
     */
    public IPage<ManagerPageVO> findPage(ManagerQueryVO managerQueryVO, Page page, RoleEnum roleEnum) {
        return baseMapper.selectManagerPage(page, roleEnum, managerQueryVO.getManagerId(), managerQueryVO.getName());
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
    @Transactional
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
        BeanUtils.copyProperties(addVO, manager);
        manager.setName(addVO.getAccount());
        manager.setRole(roleEnum);
        manager.setPassword(password);
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
    @Transactional
    public ManagerPageVO update(ManagerEditVO editVO, RoleEnum roleEnum) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("manager_id", editVO.getManagerId()).eq("role", roleEnum);
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);

        manager.setAccount(editVO.getAccount());
        saveOrUpdate(manager);

        return toManagerPageVO(manager);
    }

    /**
     * 开启或禁用管理员
     *
     * @param managerStateVO
     */
    @Transactional
    public ManagerPageVO handleDisabled(ManagerStateVO managerStateVO, RoleEnum roleEnum) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("manager_id", managerStateVO.getManagerId()).eq("role", roleEnum);
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        Assert.isTrue(!Objects.equals(manager.getRole(), RoleEnum.ROOT), MessageCodes.ROOT_CANNOT_DISABLED);
        if (manager.getDisabled()) {
            shiroTokenService.logout(managerStateVO.getManagerId().toString());
        }
        baseMapper.update(new Manager(), new UpdateWrapper<Manager>().lambda().set(Manager::getDisabled, !manager.getDisabled()).eq(Manager::getManagerId, manager.getManagerId()));
        return toManagerPageVO(manager);
    }

    /**
     * 删除管理员
     *
     * @param idList
     */
    @Transactional
    public void delete(Set<Integer> idList, RoleEnum roleEnum) {
        Manager manager;
        for (Integer managerId : idList) {
            QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("manager_id", managerId).eq("role", roleEnum);
            manager = baseMapper.selectOne(wrapper);
            if (Objects.nonNull(manager)) {
                Assert.isTrue(!Objects.equals(manager.getRole(), RoleEnum.ROOT), MessageCodes.ROOT_CANNOT_DELETE);
                shiroTokenService.login(managerId.toString());
                baseMapper.deleteById(managerId);
            }
        }
    }

    @Transactional
    public ManagerPageVO resetPassword(ManagerResetPasswordVO managerResetPasswordVO, RoleEnum roleEnum) {
        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("manager_id", managerResetPasswordVO.getManagerId()).eq("role", roleEnum);
        Manager manager = baseMapper.selectOne(wrapper);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
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

    /**
     * 修改管理员名称和密码
     *
     * @param vo
     * @return
     *//*
    public ManagerPageVO editPassword(ManagerEditPasswordVO vo, RoleEnum roleEnum) {
        Manager manager = managerDao.findById(vo.getManagerId()).orElse(null);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        String md5Password = DigestUtils.md5DigestAsHex((BcryptUtil.decrypt(vo.getPassword()) + salt).getBytes());
        manager.setPassword(md5Password);
        return toManagerPageVO(manager);
    }

    public ManagerPageVO handleRole(ManagerRoleVO managerRoleVO) {
        Manager manager = managerDao.findById(managerRoleVO.getManagerId()).orElse(null);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);

        Manager currentManager = managerDao.findById(UserHelper.getUserId()).orElse(null);
        Assert.notNull(currentManager, MessageCodes.MANAGER_NOT_EXIST);

        Assert.isTrue(currentManager.getRole().ordinal() < manager.getRole().ordinal(),MessageCodes.NOT_ALLOW_SUPERIOR_ROLE);
        manager.setRole(managerRoleVO.getRole());
        managerDao.save(manager);

        return toManagerPageVO(manager);
    }*/
}
