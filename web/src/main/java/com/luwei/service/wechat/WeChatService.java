package com.luwei.service.wechat;

import com.luwei.model.parent.Parent;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.parent.ParentService;
import com.luwei.utils.WeiXinUtils;
import com.riversoft.weixin.common.oauth2.OpenUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-14
 */
@Service
@Slf4j
public class WeChatService {

    @Resource
    private ParentService parentService;

    @Resource
    private ShiroTokenService shiroTokenService;

    /**
     * 微信公众号授权
     * @param code
     * @param state
     * @return
     */
    public String authorize(String code, String state) {
        //调用工具类
        OpenUser openUser = WeiXinUtils.webSiteLogin(code, state);

        String userToken = authorize(openUser);
        log.info("生成token：{}", userToken);
        return "redirect:" + state + "?token=" + userToken;
    }

    @Transactional
    public String authorize(OpenUser openUser) {
        //Parent parent = parentService.findByOpenId(user.getOpenId());
        Parent parent = parentService.getById(Integer.valueOf(openUser.getOpenId()));
        log.info("用户的昵称：{}", openUser.getNickName());
        //判断是否是新用户，是的话保存到数据库
        if (parent == null) {
            parent = new Parent();
            parent.setCreateTime(LocalDateTime.now());
            parent.setUpdateTime(LocalDateTime.now());
            // TODO 复制属性过去
            //BeanUtils.copyProperties(user, wxUser, "privilege");

            parentService.save(parent);
        }
        Integer userId = parent.getParentId();
        shiroTokenService.logout(userId.toString());
        String token = shiroTokenService.login(userId.toString());
        return token;
    }
}
