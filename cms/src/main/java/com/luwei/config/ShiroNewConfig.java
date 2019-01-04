package com.luwei.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决 @RequiresRoles 注解不起作用的问题
 * <p>
 * Author: huanglp
 * Date: 2019-01-04
 */
@Configuration
public class ShiroNewConfig {

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        // authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

}
