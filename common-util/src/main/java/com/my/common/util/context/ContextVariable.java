package com.my.common.util.context;

import com.my.common.util.info.UserInfo;

/**
 * dubbo调用上下文传递-服务提供端上下文线程内存
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class ContextVariable{
    private static ThreadLocal<String> companyCodeThreadLocal  = new ThreadLocal<>();  //当前人所属公司编码
    private static ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>(); //当前登录人信息
    private static ThreadLocal<String> currentRequestURLLocal = new ThreadLocal<>(); //浏览器请求的当前url路径
    private static ThreadLocal<String> currentRequestSysCodeLocal = new ThreadLocal<>(); //浏览器请求的所属系统的编码

    public static void setCompanyCode(String code){
        companyCodeThreadLocal.set(code);
    }

    public static String getCompanyCode(){
        return companyCodeThreadLocal.get();
    }

    public static void clearCompanyCode(){
        companyCodeThreadLocal.remove();
    }

    public static void setUserInfo(UserInfo userInfo){
        userInfoThreadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo(){
        return userInfoThreadLocal.get();
    }

    public static void clearUserInfo(){
        userInfoThreadLocal.remove();
    }

    public static void setCurrentRequestURL(String currentRequestURL){
        currentRequestURLLocal.set(currentRequestURL);
    }

    public static String getCurrentRequestURL(){
        return currentRequestURLLocal.get();
    }

    public static void clearCurrentRequestURL(){
        currentRequestURLLocal.remove();
    }

    public static void setCurrentRequestSysCode(String currentRequestSysCode){
        currentRequestSysCodeLocal.set(currentRequestSysCode);
    }

    public static String getCurrentRequestSysCode(){
       return currentRequestSysCodeLocal.get();
    }

    public static void clearCurrentRequestSysCode(){
        currentRequestSysCodeLocal.remove();
    }
}
