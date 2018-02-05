package com.my.common.util.info;

import java.io.Serializable;

/**
 * 组织机构信息封装类（全局通用）
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/30
 */
public class OrganizationInfo implements Serializable ,Cloneable{
    private static final long serialVersionUID = 1382940210792297850L;
    private long organizaitonId ;
    private String organizationCode;
    private long organizationParent;
    private String organizaitonPath;
    private String organizationName;

    public long getOrganizaitonId() {
        return organizaitonId;
    }

    public void setOrganizaitonId(long organizaitonId) {
        this.organizaitonId = organizaitonId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public long getOrganizationParent() {
        return organizationParent;
    }

    public void setOrganizationParent(long organizationParent) {
        this.organizationParent = organizationParent;
    }

    public String getOrganizaitonPath() {
        return organizaitonPath;
    }

    public void setOrganizaitonPath(String organizaitonPath) {
        this.organizaitonPath = organizaitonPath;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public OrganizationInfo clone(){
        OrganizationInfo i = new OrganizationInfo();
        i.setOrganizaitonId(this.getOrganizaitonId());
        i.setOrganizaitonPath(this.getOrganizaitonPath());
        i.setOrganizationCode(this.getOrganizationCode());
        i.setOrganizationName(this.getOrganizationName());
        i.setOrganizationParent(this.getOrganizationParent());
        return i;
    }

}
