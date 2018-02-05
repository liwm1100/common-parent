package com.my.common.security.shiro.dao;

import com.my.common.cache.RedisManagerFactory;
import com.my.common.cache.RedisRunner;
import com.my.common.util.SerializeUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 继承shiro的{@link  AbstractSessionDAO},负责对session的缓存托管。
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    //session 超时时间 ，默认1800
    private int timeOutSec = 1800;


    /** session缓存key的前缀 */
    private String keyPrefix = "my_shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * 保存session
     * @param session {@link Session}
     */
    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }

        final byte[] key = getByteKey(session.getId());
        final byte[] value = SerializeUtils.serialize(session);
        session.setTimeout(timeOutSec * 1000);
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    if (0 == timeOutSec)
                        jedis.set(key, value);
                    else
                        jedis.setex(key, timeOutSec, value);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除该session的缓存
     * @param session {@link Session}
     */
    @Override
    public void delete(final Session session) {

        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    jedis.del(getByteKey(session.getId()));
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获得全部存活的session
     * @return {@link Collection<Session>}
     */
    @Override
    public Collection<Session> getActiveSessions() {
        try {
            final  Set<Session> sessions = new HashSet<>();
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    Set<byte[]> keys = jedis.keys((keyPrefix + "*").getBytes("UTF-8"));
                    for(byte[] key :keys){
                        Session s = (Session) SerializeUtils.deserialize(jedis.get(key));
                        sessions.add(s);
                    }
                }
            });
            return sessions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行创建session
     * @param session {@link Session}
     * @return sessionId
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    /**
     * 执行读取session
     * @param sessionId
     * @return {@link Session}
     */
    @Override
    protected Session doReadSession(final Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        try {
           final Session[] session = new Session[1];
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                     session[0] = (Session) SerializeUtils.deserialize(jedis.get(getByteKey(sessionId)));
                }
            });
            return session[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得byte[]型的key
     *
     * @param sessionId
     * @return byte[]
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        try {
            return preKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw  new RuntimeException(e);
        }
    }

    /**
     * 返回session key的前缀
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * 设置session key 的前缀
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 返回超时时间 ，单位秒
     * @return timeOutSec
     */
    public int getTimeOutSec() {
        return timeOutSec;
    }

    /**
     * 设置超时时间，单位秒
     * @param timeOutSec
     */
    public void setTimeOutSec(int timeOutSec) {
        this.timeOutSec = timeOutSec;
    }
}