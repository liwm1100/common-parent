package com.my.common.security.encrypt;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 实现shiro的接口，密码效验
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/26
 */
public class PasswordMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
        UsernamePasswordToken ut = (UsernamePasswordToken) token;
        String password = (String) info.getCredentials();
        return PasswordUtil.passwordEncoder.matches(new String(ut.getPassword()) ,password);
        /*String tokenPwd = PasswordUtil.encrytPwd(new String(ut.getPassword()));
        return equals(tokenPwd, password);*/
    }

    public boolean doMatch(String pwd ,String encodedPwd){
        return PasswordUtil.passwordEncoder.matches(pwd ,encodedPwd);
    }
}