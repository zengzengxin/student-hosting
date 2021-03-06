package com.luwei.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RedisKeyPrefix;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.BcryptUtil;
import com.luwei.model.manager.Manager;
import com.luwei.model.manager.ManagerMapper;
import com.luwei.model.manager.pojo.LoginSuccessVO;
import com.luwei.model.manager.pojo.ManagerLoginVO;
import com.luwei.module.shiro.service.ShiroTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *
 * @author luwei
 **/
@Slf4j
@Service
public class LoginService extends ServiceImpl<ManagerMapper, Manager> {

    @Value("${luwei.config.salt}" )
    private String salt;

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 管理员登录
     *
     * @param loginVO
     * @return LoginSuccessVO
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginSuccessVO login(ManagerLoginVO loginVO) {
        //从redis中获得该验证码
        String rightCaptcha = stringRedisTemplate.opsForValue().get(RedisKeyPrefix.captcha(loginVO.getUuid()));
        stringRedisTemplate.delete(RedisKeyPrefix.captcha(loginVO.getUuid()));
        if (StringUtils.isEmpty(rightCaptcha)) {
            //验证码已失效
            throw new ValidationException(MessageCodes.AUTH_PICCAPTCHA_LOST);
        } else {
            //判断验证码是否正确
            if (!rightCaptcha.equals(loginVO.getCaptcha())) {
                //验证码错误
                throw new ValidationException(MessageCodes.AUTH_PICCAPTCHA_WRONG);
            } else {
                //判断账号密码是否正确
                String md5Password;
                try {
                    log.info("loginVO.getPassword:[{}]", loginVO.getPassword());
                    String decrypt = BcryptUtil.decrypt(loginVO.getPassword());
                    log.info("decrypt:[{}]===salt:[{}]", decrypt, salt);
                    md5Password = DigestUtils.md5DigestAsHex((decrypt + salt).getBytes());
                    log.info("md5Password:[{}]", md5Password);
                } catch (Exception e) {
                    log.error("RSAUtil.decrypt exception", e);
                    throw new IllegalArgumentException(MessageCodes.RSAUtil_DECRYPT_ERROR);
                }
                QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>().eq("account", loginVO.getAccount()).eq("password", md5Password).eq("deleted", false);
                Manager manager = baseMapper.selectOne(wrapper);
                log.info("manager:[{}]", manager);
                if (Objects.nonNull(manager)) {
                    if (manager.getDisabled()) {
                        //账号已禁用
                        throw new ValidationException(MessageCodes.ACCOUNT_HAS_DISABLED);
                    }
                    int managerId = manager.getManagerId();
                    int role = manager.getRole().ordinal();

                    manager.setLastLoginTime(LocalDateTime.now());
                    saveOrUpdate(manager);

                    return new LoginSuccessVO(RoleEnum.values()[role], shiroTokenService.login(String.valueOf(managerId), String.valueOf(role)), manager.getName());
                } else {
                    //密码错误或账户不存在
                    throw new ValidationException(MessageCodes.AUTH_ACCOUNT_PASSWORD_WRONG);
                }
            }
        }
    }

}
