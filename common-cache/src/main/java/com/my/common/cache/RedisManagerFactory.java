package com.my.common.cache;

import com.my.common.util.FrameworkProperties;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * RedisManager 工厂类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/17
 *
 */
public class RedisManagerFactory {
    private static RedisManager redisManager = null;

    static {
        String type = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_TYPE);
        if("ordinary".equals(type)){
            initOrdinaryRedisManager();
        }else if("sentinel".equals(type)){
            initSentinelRedisManager();
        }else{
            // not have anything to do
        }
    }

    /**
     * 初始化主从哨兵jedis pool
     */
    private static void initSentinelRedisManager(){
        String masterName = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_SENTINEL_MASTER_NAME);
        String sentinels = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_SENTINEL_HOSTS);
        String pwd = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_SENTINEL_PASSWORD);
        String[] sentinelArrary = sentinels.split(",");
        Set<String> sentinelSet = new HashSet<>();
        sentinelSet.addAll(Arrays.asList(sentinelArrary));
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName,sentinelSet,getPoolConfig(),pwd);
        redisManager = new RedisManager(jedisSentinelPool);
    }

    /**
     * 初始化集群jedis pool
     */
    private static void initSharedingRedisManager(){

    }

    /**
     * 初始化普通 jedis pool
     */
    private static void initOrdinaryRedisManager(){
        String host = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_ORDINARY_HOST);
        String port = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_ORDINARY_PORT);
        String timeout = FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_TIMEOUT);

        JedisPool jedisPool = new JedisPool(getPoolConfig(),host,Integer.parseInt(port) ,Integer.parseInt(timeout));
        redisManager = new RedisManager(jedisPool);
    }

    /**
     * 初始化jedis pool 的配置信息
     * @return {@link JedisPoolConfig}
     */
    private static JedisPoolConfig getPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_MAXIDLE)));
        config.setMaxTotal(Integer.parseInt(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_MAXTOTAL)));
        config.setMinIdle(Integer.parseInt(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_MINIDLE)));
        config.setMaxWaitMillis(Long.parseLong(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_MAXWAIT)));
        config.setTestOnBorrow(Boolean.parseBoolean(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_TESTONBORROW)));
        config.setTestOnReturn(Boolean.parseBoolean(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_TESTONRETURN)));
        config.setTestWhileIdle(Boolean.parseBoolean(FrameworkProperties.getPropertyValue(FrameworkProperties.FRAMEWORK_REDIS_POOL_TESTWHILEIDLE)));
        return  config;
    }

    private  RedisManagerFactory (){}

    /**
     * 获得一个RedisManager
     * @return {@link RedisManager}
     */
    public static RedisManager getRedisManager(){
        if(null == redisManager)
            throw new IllegalStateException("尚未初始化Redis 连接池");
        return redisManager;
    }
}
