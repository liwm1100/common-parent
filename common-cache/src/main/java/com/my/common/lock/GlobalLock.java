package com.my.common.lock;

import com.my.common.cache.RedisManagerFactory;
import com.my.common.cache.RedisRunner;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

/**
 *  全局分布式锁
 * @author
 * @version 1.0
 * @project common
 * @date 2016/11/9
 *
 */
public class GlobalLock {
    private static final int DEFAULT_INTERVAL_TIME_MS = 1000;
    private static final String GLOBAL_LOCK_PROFIX = "COM.my.GLOBAL.LOCK.PROFIX.";
    private int lockTimeOutMSec = 3000;
    private String lockKey = null;

    /**
     * 分布式锁构造器
     *
     * <P> <h2>注意：通过该方法构建的分布式锁失效时间为3秒<h2/>
     *
     *  @param lockKey 分布式锁的key
     */
    public  GlobalLock(String lockKey){
        this(3000 ,lockKey);
    }

    /**
     * 分布式锁构造器
     * @param lockTimeOutMSec 分布式锁失效时间 ,单位 <b>毫秒</b> 。超时时间必须大于500毫秒
     * @param lockKey 分布式锁的key
     */
    public GlobalLock(int lockTimeOutMSec,String lockKey){
        if(lockTimeOutMSec <= 500 ){
            throw new IllegalArgumentException("lockTimeOut 必须大于 500 ms ");
        }
        if(StringUtils.isBlank(lockKey)){
            throw new IllegalArgumentException("lockKey 不能为空");
        }
        this.lockTimeOutMSec = lockTimeOutMSec;
        this.lockKey = GLOBAL_LOCK_PROFIX+lockKey;
    }

    /**
     * 非阻塞单次锁
     * @return {@code true} 表示获得锁，{@dode false}表示未获得锁
     * @throws GlobalLockException 当与redis交互时如出现错误会抛出此异常
     */
    public synchronized boolean tryLock() throws GlobalLockException {
        final boolean[] result = new boolean[1];
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    long now = System.currentTimeMillis();
                    long timeOut = now + lockTimeOutMSec + 1;
                    long rs = jedis.setnx(lockKey,String.valueOf(timeOut));
                    if(rs == 1l){
                        result[0] = true;
                    }else {
                        String r = jedis.get(lockKey);
                        if (now > Long.parseLong(null == r ? "0" : r)) {  //当前时间大于锁超时时间
                            String r1 = jedis.getSet(lockKey, String.valueOf(timeOut));
                            if (now > Long.parseLong(null == r1 ? "0" : r1)) {  //同步命令,当前时间大于锁超时时间， 会导致锁超时时间延后，但会保证独占性
                                result[0] = true;
                            }else {
                                result[0] = false;
                            }
                        } else {
                            result[0] = false;
                        }

                    }
                }
            });
        } catch (Exception e) {
           throw new GlobalLockException(e);
        }
        return result[0];
    }

    /**
     * 阻塞自旋锁
     * <p>自旋时间为1000 ms<p/>
     * @throws GlobalLockException 当与redis交互时如出现错误会抛出此异常
     */
    public synchronized void lock() throws GlobalLockException {
        boolean unlocked = true;
        do{
            unlocked = !tryLock();
            if(unlocked){
                try {
                    Thread.sleep(DEFAULT_INTERVAL_TIME_MS);
                } catch (InterruptedException e) {
                }
            }
        }while (unlocked);
    }

    /**
     * 释放锁资源
     * @throws GlobalLockException 当与redis交互时如出现错误会抛出此异常
     */
    public synchronized void unLock() throws GlobalLockException {
        try {
            RedisManagerFactory.getRedisManager().run(new RedisRunner() {
                @Override
                public void run(Jedis jedis) throws Exception {
                    long now = System.currentTimeMillis();
                    String r = jedis.get(lockKey);
                    if(null == r || Float.parseFloat(r) > now){  //锁不存在 或有可能以备其他进程获得
                        return;
                    }else{
                        jedis.del(lockKey);
                    }
                }
            });
        } catch (Exception e) {
            throw new GlobalLockException(e);
        }
    }




}
