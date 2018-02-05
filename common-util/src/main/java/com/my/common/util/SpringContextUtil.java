package com.my.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring context工具
 * @Project common
 * @version 1.0
 * @Author  cai
 * @Date    2016年10月17日
 */
public class SpringContextUtil implements ApplicationContextAware {
    // Spring应用上下文环境
    private static ApplicationContext applicationContext;
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        if(null == SpringContextUtil.applicationContext && null == applicationContext.getParent()) {
            SpringContextUtil.applicationContext = applicationContext;
        }

    }
    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    /**
     * 获取对象
     *
     * @param name beanName
     * @return Object
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        if(null != applicationContext){
            return applicationContext.getBean(name);
        }else{
            return null;
        }
    }

    /**
     * 获取对象
     *
     * @param type bean class
     * @return Object
     */
    public static <T> T getBean(Class<T> type) {
        if(null != applicationContext){
            return applicationContext.getBean(type);
        }else{
            return null;
        }
    }
}
