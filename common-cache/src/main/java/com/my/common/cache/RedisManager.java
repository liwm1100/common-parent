package com.my.common.cache;


import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 *  redis 连接池管理
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/17
 *
 */
public class RedisManager {

    private Pool<Jedis> jedisPool = null;

    /**
     * 构造器
     * @param jedisPool 为池化的jedis {@link Pool}
     */
    RedisManager(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 销毁redis client pool
     */
    void destroy() {
        if (null != jedisPool) {
            jedisPool.destroy();
        }
    }

    /**
     * jedis 命令执行方法.该方法已经封装了对池对象的管理。
     * @param runner 是需要实现的具体的redis执行内容 {@link RedisRunner}
     * @throws Exception 当操作jedis时爆出的异常，向外抛出
     */
    public void run(RedisRunner runner) throws Exception {
        if (null == jedisPool)
            throw new IllegalStateException("尚未初始化Redis 连接池");
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (null == jedis)
                throw new IllegalStateException("已经初始化redis连接池 ,但未能成功初始化jedis");
            runner.run(jedis);
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }

}