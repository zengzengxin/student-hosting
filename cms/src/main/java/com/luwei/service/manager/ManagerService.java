package com.luwei.service.manager;

import com.luwei.common.constants.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.BcryptUtil;
import com.luwei.common.util.CommonSpecUtil;
import com.luwei.model.manager.Manager;
import com.luwei.model.manager.ManagerDao;
import com.luwei.model.manager.pojo.*;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.module.shiro.service.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
public class ManagerService {

    @Resource
    private CommonSpecUtil<Manager> specUtil;

    @Resource
    private ManagerDao managerDao;

    @Resource
    private ShiroTokenService shiroTokenService;

    @Value("${luwei.config.salt}")
    private String salt;

    /**
     * 获得管理员列表
     *
     * @param managerQueryVO
     * @param pageable
     * @return Page<ManagerPageVO>
     */
    public Page<ManagerPageVO> findPage(ManagerQueryVO managerQueryVO, Pageable pageable) {
        Specification<Manager> spec = Specification.where(specUtil.equal("deleted", false))
                .and(specUtil.equal("managerId", managerQueryVO.getManagerId()))
                .and(specUtil.like("name", managerQueryVO.getName()));
        return managerDao.findAll(spec, pageable).map(this::toManagerPageVO);
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
    public ManagerPageVO add(ManagerAddVO addVO) {
        String account = addVO.getAccount();

        if (!Objects.equals("luwei", account)
                && addVO.getRole().equals(RoleEnum.ROOT)) {
            throw new IllegalArgumentException(MessageCodes.NOT_ALLOW_ADD_LUWEI_EXCEPT_ROOT);
        }

        String password;
        try {
            password = DigestUtils.md5DigestAsHex((BcryptUtil.decrypt(addVO.getPassword()) + salt).getBytes());
        } catch (Exception e) {
            log.error("RSAUtil.decrypt exception", e);
            throw new IllegalArgumentException(MessageCodes.RSAUtil_DECRYPT_ERROR);
        }
        Manager manager = managerDao.findByAccountAndDeletedIsFalse(account);
        Assert.isNull(manager, MessageCodes.ACCOUNT_ALREADY_EXIST);
        manager = new Manager();
        BeanUtils.copyProperties(addVO, manager);
        manager.setPassword(password);
        managerDao.save(manager);
        return toManagerPageVO(manager);
    }

    /**
     * 去编辑管理员
     *
     * @param managerId
     * @return ManagerEditVO
     */
    public ManagerEditVO toEdit(int managerId) {
        Manager manager = managerDao.findById(managerId).orElse(null);
        Assert.notNull(manager,MessageCodes.MANAGER_NOT_EXIST);

        if (!Objects.equals(manager.getRole(), RoleEnum.ROOT)
                && !Objects.equals(managerId, UserHelper.getUserId())) {
            throw new ValidationException(MessageCodes.NOT_ROOT_ONLY_CAN_EDIT_SELF_INFO);
        }

        ManagerEditVO editVO = new ManagerEditVO();
        editVO.setManagerId(managerId);
        editVO.setName(manager.getName());
        return editVO;
    }

    /**
     * 更新管理员
     *
     * @param editVO
     * @return
     */
    @Transactional
    public ManagerPageVO update(ManagerEditVO editVO) {
        Manager manager = managerDao.findById(editVO.getManagerId()).orElse(null);
        Assert.notNull(manager,MessageCodes.MANAGER_NOT_EXIST);

        if (!Objects.equals(manager.getRole(), RoleEnum.ROOT)
                && !Objects.equals(editVO.getManagerId(), UserHelper.getUserId())) {
            throw new ValidationException(MessageCodes.NOT_ROOT_ONLY_CAN_EDIT_SELF_INFO);
        }

        manager.setName(editVO.getName());
        managerDao.save(manager);

        return toManagerPageVO(manager);
    }

    /**
     * 开启或禁用管理员
     *
     * @param managerStateVO
     */
    @Transactional
    public ManagerPageVO handleDisabled(ManagerStateVO managerStateVO) {
        Manager manager = managerDao.findById(managerStateVO.getManagerId()).orElse(null);
        Assert.notNull(manager,MessageCodes.MANAGER_NOT_EXIST);
        Assert.isTrue(Objects.equals(manager.getRole(), RoleEnum.ADMIN), MessageCodes.ADMIN_CANNOT_DISABLED);
        manager.setDisabled(!manager.getDisabled());
        if (manager.getDisabled()) {
            shiroTokenService.logout(managerStateVO.getManagerId().toString());
        }
        managerDao.save(manager);
        return toManagerPageVO(manager);
    }

    /**
     * 删除管理员
     *
     * @param idList
     */
    @Transactional
    public void delete(Set<Integer> idList) {
        for (Integer managerId : idList) {
            Manager manager = managerDao.findByManagerIdAndDeletedIsFalse(managerId);
            if (Objects.nonNull(manager)) {
                Assert.isTrue(Objects.equals(manager.getRole(), RoleEnum.ADMIN), MessageCodes.ADMIN_CANNOT_DELETE);
                shiroTokenService.login(managerId.toString());
                manager.setDeleted(true);
                managerDao.save(manager);
            }
        }
    }

    @Transactional
    public ManagerPageVO resetPassword(ManagerResetPasswordVO managerResetPasswordVO) {

        Manager manager = managerDao.findById(managerResetPasswordVO.getManagerId()).orElse(null);
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        if (!Objects.equals(manager.getRole(), RoleEnum.ROOT)
                && !Objects.equals(managerResetPasswordVO.getManagerId(), UserHelper.getUserId())) {
            throw new ValidationException(MessageCodes.NOT_ROOT_ONLY_CAN_EDIT_SELF_INFO);
        }
        String md5Password;
        try {
            md5Password = DigestUtils.md5DigestAsHex((BcryptUtil.decrypt(managerResetPasswordVO.getPassword()) + salt).getBytes());
        } catch (Exception e) {
            log.error("RSAUtil.decrypt exception", e);
            throw new IllegalArgumentException(MessageCodes.RSAUtil_DECRYPT_ERROR);
        }
        manager.setPassword(md5Password);
        managerDao.save(manager);

        return toManagerPageVO(manager);
    }

    /**
     * 修改管理员名称和密码
     *
     * @param vo
     * @return
     */
    public ManagerPageVO editPassword(ManagerEditPasswordVO vo) {
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
    }
}
