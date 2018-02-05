package com.my.common.security.shiro.dto;

import java.io.Serializable;

 /**
 * 用户数据传输对象
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -6916331691051220785L;
    /** 用户id */
    private long userId;
    /** 用户登录名 */
    private String loginName;
    /** 用户密码 */
    private String pwd;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
