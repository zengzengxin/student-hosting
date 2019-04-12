package com.luwei.common.holder;

import org.springframework.context.ApplicationContext;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
public class SpringBeanHolder {

    /**
     * 静态变量applicationContext
     */
    private static ApplicationContext applicationContext;

    /**
     * 将applicationContext设置到静态变量中.
     *
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanHolder.applicationContext = applicationContext;
    }

    /**
     * 获取静态变量中的applicationContext.
     */
    @SuppressWarnings("WeakerAccess" )
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 从静态变量applicationContext中得到Bean, 先按照name获取, 没有再按clazz.
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
