package com.my.common.security.shiro.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 模块权限数据传输对象
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class ModulePermissionDTO implements Serializable {
    private static final long serialVersionUID = -5716351926499774284L;
    /** app 编码 */
    private String appCode;
    /**  模块编码 */
    private String moduleCode;
    /** 模块url**/
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModulePermissionDTO that = (ModulePermissionDTO) o;

        if (!appCode.equals(that.appCode)) return false;
        return moduleCode.equals(that.moduleCode);

    }

    @Override
    public int hashCode() {
        int result = StringUtils.trimToEmpty(appCode).hashCode();
        result = 31 * result + moduleCode.hashCode();
        return result;
    }
}
