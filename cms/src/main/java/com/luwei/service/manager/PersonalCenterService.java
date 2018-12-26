package com.luwei.service.manager;

import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BcryptUtil;
import com.luwei.model.manager.Manager;
import com.luwei.model.manager.ManagerMapper;
import com.luwei.model.manager.pojo.ManagerPageVO;
import com.luwei.model.manager.pojo.PersonalCenterVO;
import com.luwei.module.shiro.service.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class PersonalCenterService {


    @Resource
    private ManagerMapper managerMapper;

    @Value("${luwei.config.salt}")
    private String salt;

    public ManagerPageVO toEdit() {
        Manager manager = managerMapper.selectById(UserHelper.getUserId());
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        ManagerPageVO managerPageVO = new ManagerPageVO();
        BeanUtils.copyProperties(manager, managerPageVO);
        return managerPageVO;
    }

    @Transactional
    public ManagerPageVO update(PersonalCenterVO personalCenterVO) {
        Manager manager = managerMapper.selectById(UserHelper.getUserId());
        Assert.notNull(manager, MessageCodes.MANAGER_NOT_EXIST);
        manager.setName(personalCenterVO.getName());
        if (Objects.nonNull(personalCenterVO.getPassword())) {
            String md5Password;
            try {
                md5Password = DigestUtils.md5DigestAsHex((BcryptUtil.decrypt(personalCenterVO.getPassword()) + salt).getBytes());
            } catch (Exception e) {
                log.error("RSAUtil.decrypt exception", e);
                throw new IllegalArgumentException(MessageCodes.RSAUtil_DECRYPT_ERROR);
            }
            manager.setPassword(md5Password);
        }
        managerMapper.insert(manager);

        ManagerPageVO managerPageVO = new ManagerPageVO();
        BeanUtils.copyProperties(manager, managerPageVO);

        return managerPageVO;
    }

}
