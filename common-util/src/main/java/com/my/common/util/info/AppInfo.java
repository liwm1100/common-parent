package com.my.common.util.info;

import java.io.Serializable;

/**
 * app信息封装类（全局通用）
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/30
 */
public class AppInfo  implements Serializable,Cloneable {

    private static final long serialVersionUID = -5038204964844106948L;
    private Long appId;
    private String code;
    private String name;
    private String domain;

    public Long getAppId() {
        return appId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public AppInfo clone() {
        AppInfo a = new AppInfo();
        a.name = this.name;
        a.appId = this.appId;
        a.code = this.code;
        a.domain = this.domain;
        return a;
    }
}
