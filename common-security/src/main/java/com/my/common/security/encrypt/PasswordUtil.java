package com.my.common.security.encrypt;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 密码加密工具栏
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/26
 */
public class PasswordUtil {

    private static byte[] salt = new byte[]{77,75,120,119,75,52,118,113,82,80,82,122,100,83,68,107,77,71,81,107,83,65};

    static StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder("IKJ-890");

    private PasswordUtil() {
    }

    public static String encrytPwd(String password) {
        return passwordEncoder.encode(password);
       /* Md5Hash md5Hash = new Md5Hash(password, salt);
        String pass = md5Hash.toBase64();
        if(pass.endsWith("==")){
        	return pass.substring(0, pass.length()-2);
        }
        return md5Hash.toBase64();*/
    }
}