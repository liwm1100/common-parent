package com.my.common.security.shiro.dto;

import java.io.Serializable;

/**
 * 角色数据传输对象
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = -7206230605594901048L;
    /** 角色id */
    private long roleId;
    /** 角色编码 */
    private String roleCode;
    /** 角色名称 **/
    private String roleName;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDTO roleDTO = (RoleDTO) o;

        if (roleId == roleDTO.roleId)
            return true;
        else
            return false;

    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (roleCode != null ? roleCode.hashCode() : 0);
        return result;
    }
}
