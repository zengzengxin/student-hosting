package com.luwei.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Configuration;

/**
 * 解决 @RequiresRoles 注解不起作用的问题
 * <p>
 * Author: huanglp
 * Date: 2019-01-04
 */
@Configuration
public class ShiroNewConfig {


    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

}
