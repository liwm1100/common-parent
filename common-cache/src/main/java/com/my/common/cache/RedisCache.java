package com.my.common.cache;

import com.my.common.util.JsonUtil;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @Project common
 * @Author
 * @Date 2017-01-09
 */
public abstract class RedisCache {

    public static void set(final String key, final String value) throws Exception{
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.set(key,value);
            }
        });

    }

    public static void set(final String key, final Object value) throws Exception{
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.set(key,JsonUtil.json(value));
            }
        });

    }

    public static void setex(final String key, final String value, final int seconds) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.setex(key, seconds, value);
            }
        });
    }

    public static void setex(final String key, final Object value, final int seconds) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.setex(key, seconds, JsonUtil.json(value));
            }
        });
    }

    public static <T> T get(final String key ,final Class<T> tClass) throws Exception {
        final Object[] objs = new Object[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                String value = jedis.get(key);
                objs[0] = null == value? value:JsonUtil.parse(value,tClass);
            }
        });
        return (T) objs[0];
    }

    public static String get(final String key) throws Exception {
        final String[] objs = new String[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                objs[0] = jedis.get(key);
            }
        });
        return objs[0];
    }

    public static void delete(final String key) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.del(key);
            }
        });
    }

    public static void delete(final String ...keys) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                    jedis.del(keys);
            }
        });
    }

    public static void deleteByPattren(final String pattern) throws Exception {
        Set<String> keys = keys(pattern);
        if(keys!=null && keys.size()>0){
            for(String key:keys){
                delete(key);
            }
        }
    }

    public static void expire(final String key ,final int seconds) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.expire(key,seconds);
            }
        });
    }

    public static void hsetex(final String key, final String field, final String value, final int seconds) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.hset(key, field, value);
                jedis.expire(key, seconds);
            }
        });
    }


    public static void hset(final String key, final String field, final String value) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.hset(key, field, value);
            }
        });
    }


    public static void hdel(final String key,final String field) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.hdel(key, field);
            }
        });
    }


    public static Map<String, String> hgetAll(final String key) throws Exception {
        final Map<String,String>[] map = new Map[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                map[0] =jedis.hgetAll(key);
            }
        });
        return map[0];
    }


    public static void hmset(final String key,final Map<String,String> map) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.hmset(key,map);
            }
        });
    }

    public static void hmsetex(final String key,final Map<String,String> map,final int seconds) throws Exception {
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                jedis.hmset(key,map);
                jedis.expire(key, seconds);
            }
        });
    }



    public boolean hexists(final String key,final String field) throws Exception {
        final boolean[] booleans = new boolean[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                booleans[0] = jedis.hexists(key, field);
            }
        });
        return booleans[0];
    }

    public static Long hlen(final String key) throws Exception {
        final long[] longs= new long[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                longs[0] = jedis.hlen(key);
            }
        });
        return longs[0];
    }

    public static long incrBy(final String incrKey,final int step) throws Exception {
        final long[] longs= new long[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                longs[0] = jedis.incrBy(incrKey, step);
            }
        });
        return longs[0];
    }

    public static long incr(final String incrKey) throws Exception {
        final long[] longs= new long[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                longs[0] = jedis.incr(incrKey);
            }
        });
        return longs[0];
    }

    public static long incrByex(final String incrKey,final int step ,final int seconds) throws Exception {
        final long[] longs= new long[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                longs[0] = jedis.incrBy(incrKey, step);
                jedis.expire(incrKey ,seconds);
            }
        });
        return longs[0];
    }

    public static long increx(final String incrKey,final int seconds) throws Exception {
        final long[] longs= new long[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                longs[0] = jedis.incr(incrKey);
                jedis.expire(incrKey,seconds);
            }
        });
        return longs[0];
    }

    public static Set<String> keys(final String pattern) throws Exception {
        final Object[] objs = new Object[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                objs[0] = jedis.keys(pattern);
            }
        });
        return (Set<String>) objs[0];
    }

    public static Boolean exists(final String key) throws Exception {
        final Object[] objs = new Object[1];
        RedisManagerFactory.getRedisManager().run(new RedisRunner() {
            @Override
            public void run(Jedis jedis) throws Exception {
                objs[0] = jedis.exists(key);
            }
        });
        return (Boolean) objs[0];
    }

}
