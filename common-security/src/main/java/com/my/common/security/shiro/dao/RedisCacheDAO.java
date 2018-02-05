package com.my.common.security.shiro.dao;

import com.my.common.cache.RedisManagerFactory;
import com.my.common.cache.RedisRunner;
import com.my.common.util.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Set;

/**
 * 实现shiro的{@link Cache}接口，负责将需要缓存的内容，保存到redis中
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class RedisCacheDAO<K, V> implements Cache<K, V> {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheDAO.class);

    private String cacheName;

    private String prefix = "my_shiro_redis_cache:";


    /**
     * 构造函数
     * @param cacheName 缓冲名称
     */
    RedisCacheDAO(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     * 从缓存中获得value
     * @param key 缓存的key
     * @return 缓冲的value
     * @throws CacheException  当获得value时发生异常，会抛出此异常
     */
    @Override
    public V get(final K key) throws CacheException {
        try {
            final Object[] o = new Object[1];
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    byte[] bytes = getCacheKey(key);
                    byte[] values = jedis.get(bytes);
                    if (values != null && values.length > 0) {
                        o[0] = getObjByByteArray(values);
                    } else {
                        o[0] = null;
                    }
                }
            });
            if (o[0] == null)
                return null;
            try {
                return (V) o[0];
            } catch (ClassCastException e) {
                throw new CacheException("持久到redis的类型，和要查询的类型不一致");
            }
        } catch (Exception e) {
            logger.error("oid=" + key + ",RedisCacheDAO get方法获取数据时发生异常", e);
            if (e instanceof CacheException) {
                throw (CacheException) e;
            } else {
                throw new CacheException(e);
            }
        }
    }

    /**
     * 缓存数据
     * @param key 缓存的key
     * @param value 缓存的value
     * @return  缓存的value
     * @throws CacheException 当缓存时发生异常，会抛出此异常
     */
    @Override
    public V put(final K key, final V value) throws CacheException {
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    byte[] keys = getCacheKey(key);
                    byte[] values = getBytes(value);
                    jedis.set(keys, values);
                }
            });
            return value;
        } catch (Exception e) {
            logger.error("oid=" + key + "-" + value + ",RedisCacheDAO get方法获取数据时发生异常", e);
            throw new CacheException(e);
        }
    }

    /**
     * 删除缓存
     * @param key 缓存的key
     * @return 缓存的value
     * @throws CacheException 当删除缓存时发生异常，会抛出此异常
     */
    @Override
    public V remove(final K key) throws CacheException {
        try {
            V value = this.get(key);
            if (null == value)
                return null;
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    byte[] keys = getCacheKey(key);
                    jedis.del(keys);
                }
            });
            return value;
        } catch (Exception e) {
            logger.error("oid=" + key + ",RedisCacheDAO remove", e);
            throw new CacheException(e);
        }
    }

    @Override
    public void clear() throws CacheException {
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    byte[] key = getCacheKey((K)"*");
                    Set<byte[]> values = jedis.keys(key);

                    if(values!=null && values.size()>0) {
                        byte[][] keysArray=new byte[values.size()][];
                        values.toArray(keysArray);
                        jedis.del(keysArray);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            logger.error("keys:"+new String(getCacheKey((K)"*"))+" RedisCacheDAO clear", e);
            throw new CacheException(e);
        }
    }

    @Override
    public int size() {
        throw new CacheException("该方法暂不支持");
    }

    @Override
    public Set<K> keys() throws CacheException {
        final Object[] objs = new Object[1];
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    objs[0] = jedis.keys(getCacheKey((K)"*"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Set<K>) objs[0];
    }

    @Override
    public Collection<V> values() {
        throw new CacheException("该方法暂不支持");
    }

    private byte[] getCacheKey(K key) {
        return new String(getPrefix()+key.toString()).getBytes();
    }


    /**
     * 构造缓存key的前缀
     * @return 缓存key的前缀
     */
    private String getPrefix() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.prefix);
        sb.append(cacheName);
        sb.append(":");
        return sb.toString();
    }

    /**
     * 对象序列化封装方法
     * @param o 对象
     * @return 序列化后结果
     * 注意:使用该方法写入的key值不支持keys方法,所以不再作为key值的生成方法
     */
    private byte[] getBytes(Object o) {
        return SerializeUtils.serialize(o);
    }

    /**
     * 对象反序列化封装方法
     * @param bytes 字节组织
     * @return {@link Object}
     */
    private Object getObjByByteArray(byte[] bytes) {
        return SerializeUtils.deserialize(bytes);
    }

}
