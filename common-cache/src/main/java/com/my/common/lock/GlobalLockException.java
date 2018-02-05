package com.my.common.lock;

/**
 * 分布式锁产生的异常
 * @author
 * @version 1.0
 * @project common
 * @date 2016/11/11
 *
 */
public class GlobalLockException extends Exception {

    public GlobalLockException(String message) {
        super(message);
    }

    public GlobalLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalLockException(Throwable cause) {
        super(cause);
    }
}
