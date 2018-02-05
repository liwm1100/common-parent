package com.my.common.security.shiro.interFace;

import com.my.common.util.info.AppInfo;
import com.my.common.security.shiro.dto.ModulePermissionDTO;
import com.my.common.security.shiro.dto.OperationPermissionDTO;
import com.my.common.security.shiro.dto.RoleDTO;
import com.my.common.security.shiro.dto.UserDTO;

import java.util.List;
import java.util.Set;


/**
 * 安全效验相关接口 ，具体实现又uc通过dubbo对外提供
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public interface ISecurityService {

    /**
     * 通过登录账号查询人员信息。
     * 注意： 此次的查询应该已经过滤的被逻辑删除等各种导致不允许被登录的信息
     *
     * @param loginName 登录名
     * @return UserDTO or null
     */
    UserDTO findUserByLoginName(String loginName);

    /**
     * 通过人员id ，查询该人员的角色集
     * @param userId
     * @return Never be null
     */
    Set<RoleDTO> queryRolesByUserId(long userId);

    /**
     *  通过人员id ，查询该人员的所具备的模块权限集
     * @param userId
     * @return Never be null
     */
    Set<ModulePermissionDTO> queryModulePermissionByUserId(long userId);

    /**
     *  通过人员id ，查询该人员的所具备的模块权限集
     * @param userId
     * @return Never be null
     */
    //Set<ModulePermissionDTO> queryModulePermission(long userId,String appCode);

    /**
     * 通过人员id ，查询该人员的所具备的操作权限集
     * @param userId
     * @return Never be null
     */
    Set<OperationPermissionDTO> queryOperationPermissionByUserId(long userId);

    /**
     * 通过人员id ，查询该人员的所具备的操作权限集
     * @param userId
     * @return Never be null
     */
    //Set<OperationPermissionDTO> queryOperationPermission(long userId,String appCode,String moduleCode);

    /**
     * 查询url对应的permission编码
     * @param url
     * @return the permission value , return null when can not find the url with appCode
     */
    String queryPermissionByURL(String url, String appCode);

    /**
     * 效验当前人的密码
     * @param pwd 密码(明文)
     * @return true 效验通过，false 效验不通过
     */
    boolean matchPwd(String pwd, long userId);

    /**
     * 效验当前人的密码
     * @param pwd 密码(明文) ,loginName 登录名
     * @return true 效验通过，false 效验不通过
     */
    boolean matchPwd(String pwd, String loginName);

    /**
     * 修改当前人的密码
     * @param pwd
     */
    void setPwd(String pwd, long userId);

    /**
     *  判断是否为忽略授权的url
     * @param url 请求路径
     * @param appId app id
     * @return true为是 ，false为否
     */
    boolean isIgnoredUrl(String url, long appId);

    /**
     * 获取用户可访问APP
     * @param userId
     * @return
     */
    List<AppInfo> getAccessAppByUser(long userId);

}