package com.my.common.security.shiro.dto;

import java.io.Serializable;

/**
 * 操作权限数据传输对象
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class OperationPermissionDTO implements Serializable {

    private static final long serialVersionUID = 6368142324567036034L;
    /** app编码 */
    private String appCode;

    /** 操作编码 */
    private String operationCode;

    /**
     * 模块url
     * @return
     */
    private String moduleURL;

    public String getModuleURL() {
        return moduleURL;
    }

    public void setModuleURL(String moduleURL) {
        this.moduleURL = moduleURL;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationPermissionDTO that = (OperationPermissionDTO) o;

        if (!appCode.equals(that.appCode)) return false;
        return operationCode.equals(that.operationCode);

    }

    @Override
    public int hashCode() {
        int result = appCode.hashCode();
        result = 31 * result + operationCode.hashCode();
        return result;
    }
}
