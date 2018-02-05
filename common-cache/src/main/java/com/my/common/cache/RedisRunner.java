package com.my.common.cache;

import redis.clients.jedis.Jedis;

/**
 *  redis命令执行器接口
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/17
 *
 */
public interface RedisRunner {

    /**
     * 业务接口实现方法
     * @param jedis jedis实例 {@link Jedis}
     * @throws Exception 当操作redis出现异常时，将向外抛出此异常
     */
    public void run(Jedis jedis) throws Exception;

}
