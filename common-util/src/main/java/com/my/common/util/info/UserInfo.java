package com.my.common.util.info;

import java.io.Serializable;

/**
 * 用户信息封装类（全局通用）
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/16
 */

public class UserInfo implements Serializable,Cloneable{

    private static final long serialVersionUID = 3100866391133258641L;

    private long userId;
    private String userCode;
    private String userName;
    private String userLoginName;
    private String userMobile;
    private String userEMail;
    private boolean systemAdmin;
    private int gender;
    private String storeNo;
    private String storeName;
    private String userShopNo;
    private String userShopName;
    private String userOrgNo;
    private String userOrgName;
    private String userJobNo;
    private String userJobName;
    private int userType;


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public boolean isSystemAdmin() {
        return systemAdmin;
    }

    public void setSystemAdmin(boolean systemAdmin) {
        this.systemAdmin = systemAdmin;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEMail() {
        return userEMail;
    }

    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserShopNo() {
        return userShopNo;
    }

    public void setUserShopNo(String userShopNo) {
        this.userShopNo = userShopNo;
    }

    public String getUserOrgNo() {
        return userOrgNo;
    }

    public void setUserOrgNo(String userOrgNo) {
        this.userOrgNo = userOrgNo;
    }

    public String getUserJobNo() {
        return userJobNo;
    }

    public void setUserJobNo(String userJobNo) {
        this.userJobNo = userJobNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserShopName() {
        return userShopName;
    }

    public void setUserShopName(String userShopName) {
        this.userShopName = userShopName;
    }

    public String getUserOrgName() {
        return userOrgName;
    }

    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
    }

    public String getUserJobName() {
        return userJobName;
    }

    public void setUserJobName(String userJobName) {
        this.userJobName = userJobName;
    }

    @Override
    public UserInfo clone(){
        UserInfo u = new UserInfo();
        u.userId = this.userId;
        u.userName = this.userName;
        u.userLoginName = this.userLoginName;
        u.userMobile = this.userMobile;
        u.userEMail = this.userEMail;
        u.systemAdmin = this.systemAdmin;
        u.gender = this.gender;
        u.storeNo = this.storeNo;
        u.storeName = this.storeName;
        u.userShopNo = this.userShopNo;
        u.userShopName = this.userShopName;
        u.userOrgNo = this.userOrgNo;
        u.userOrgName = this.userOrgName;
        u.userJobNo = this.userJobNo;
        u.userJobName = this.userJobName;
        u.userCode = this.userCode;
        u.userType = this.userType;
        return u;
    }
}
