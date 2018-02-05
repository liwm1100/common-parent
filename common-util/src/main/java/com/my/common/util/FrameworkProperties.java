package com.my.common.util;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 平台级配置文件
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/3
 */
public class FrameworkProperties {

    private static final String FRAMEWORK_PROPERTIES_FILE_NAME = "framework.properties";
    private static final String APP_NAME_KEY = "com.my.framework.app.name";
    private static final String DYNAMIC_APP = "com.my.framework.dynamic.app";
    private static final Properties FRAMEWORK_PROPERTIES = new Properties();

    public static final String FRAMEWORK_REDIS_POOL_TYPE = "redis.pool.type";
    public static final String FRAMEWORK_REDIS_POOL_MAXTOTAL ="redis.pool.maxTotal";
    public static final String FRAMEWORK_REDIS_POOL_MAXIDLE ="redis.pool.maxIdle";
    public static final String FRAMEWORK_REDIS_POOL_MINIDLE ="redis.pool.minIdle";
    public static final String FRAMEWORK_REDIS_POOL_MAXWAIT ="redis.pool.maxWait";
    public static final String FRAMEWORK_REDIS_POOL_TESTONBORROW ="redis.pool.testOnBorrow";
    public static final String FRAMEWORK_REDIS_POOL_TESTONRETURN ="redis.pool.testOnReturn";
    public static final String FRAMEWORK_REDIS_POOL_TESTWHILEIDLE ="redis.pool.testWhileIdle";
    public static final String FRAMEWORK_REDIS_POOL_TIMEOUT ="redis.pool.timeOut";

    public static final String FRAMEWORK_REDIS_ORDINARY_HOST = "redis.ordinary.host";
    public static final String FRAMEWORK_REDIS_ORDINARY_PORT ="redis.ordinary.port";

    public static final String FRAMEWORK_REDIS_SENTINEL_MASTER_NAME = "redis.sentinel.master.name";
    public static final String FRAMEWORK_REDIS_SENTINEL_HOSTS = "redis.sentinel.hosts";
    public static final String FRAMEWORK_REDIS_SENTINEL_PASSWORD = "redis.sentinel.password";

    static {
        InputStream inputStream = FrameworkProperties.class.getClassLoader().getResourceAsStream(FrameworkProperties.FRAMEWORK_PROPERTIES_FILE_NAME);
        if (null == inputStream)
            throw new RuntimeException("初始化环境异常,初始化读取framework.properties文件异常 ，请将该文件放入classpath");
        try {
            FRAMEWORK_PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("初始化环境异常,初始化读取framework.properties文件异常，请将该文件放入classpath", e);
        }
    }

    /**
     * 获取当前APP名称
     *
     * @return 当前APP名称，无法查到返回null
     */
    public static String getCurrentAppName() {
        return FrameworkProperties.getTrimPropertyValue(FrameworkProperties.APP_NAME_KEY);
    }

    public static String getDynamicApp(){
        return FrameworkProperties.getTrimPropertyValue(FrameworkProperties.DYNAMIC_APP);
    }

    /**
     * 返回配置value
     * @param key
     * @return  the property value or {@code null} when can not found value by the key or the key is a blank
     */
    public static String getPropertyValue(String key){
        return StringUtils.isBlank(key)? null : FrameworkProperties.getTrimPropertyValue(key);
    }

    private static String getTrimPropertyValue(String key){
        String value = FrameworkProperties.FRAMEWORK_PROPERTIES.getProperty(key);
        return null == value? null : value.trim();
    }

}
