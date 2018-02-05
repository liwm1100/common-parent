package com.my.common.security.shiro.listener;

import com.my.common.security.shiro.dao.RedisSessionDAO;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Session 事件监听器 ，实现自{@link SessionListener}
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class CustomSessionListener implements SessionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Override
    public void onStart(Session session) {
        logger.debug("session {} onStart", session.getId());
    }

    @Override
    public void onStop(Session session) {
    	redisSessionDAO.delete(session);
        logger.debug("session {} onStop", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
    	redisSessionDAO.delete(session);
        logger.debug("session {} onExpiration", session.getId());
    }

}