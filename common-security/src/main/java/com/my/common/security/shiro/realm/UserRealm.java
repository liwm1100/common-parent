package com.my.common.security.shiro.realm;

import com.my.common.security.shiro.dto.ModulePermissionDTO;
import com.my.common.security.shiro.dto.OperationPermissionDTO;
import com.my.common.security.shiro.dto.RoleDTO;
import com.my.common.security.shiro.dto.UserDTO;
import com.my.common.security.shiro.interFace.ISecurityService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户授权实现类，继承自{@link AuthorizingRealm}
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */

public class UserRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(UserRealm.class);
	
	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

	private ISecurityService iSecurityService;

	/**
	 * 修改shiro的权限匹配方式
	 * @param permission
	 * @param info
	 * @return
	 */

	@Override
	protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
		Collection<Permission> perms = getPermissions(info);
		if (perms != null && !perms.isEmpty()) {
			for (Permission perm : perms) {
				if (perm.equals(permission)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 获取用户权限方法
	 * @see AuthorizingRealm#doGetAuthorizationInfo(PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		if(null == this.iSecurityService) {
			throw new AccountException("未初始化安全验证的 iSecurityService 服务");
		}
		UserDTO userDTO = null ;
		try{
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户信息" ,userName);
			userDTO = iSecurityService.findUserByLoginName(userName);
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 成功查询当前用户信息" ,userName);
		}catch (Exception e){
			logger.warn("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户信息,发生异常" ,userName);
			throw new AccountException(e);
		}

		// 该账号不存在或已过期被禁用
		if(null == userDTO){
			return new SimpleAuthorizationInfo();
		}
		//角色名的集合
		Set<String> roles = new HashSet<>();
		try{
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户角色信息" ,userDTO.getUserId());
			Set<RoleDTO> roleDTOSet = iSecurityService.queryRolesByUserId(userDTO.getUserId());
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 成功查询当前角色用户信息" ,userDTO.getUserId());
			for(RoleDTO roleDTO : roleDTOSet){
				roles.add(roleDTO.getRoleName());
			}
		}catch (Exception e){
			logger.warn("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户角色信息,发生异常" ,userDTO.getUserId());
			throw new AccountException(e);
		}

		//权限名的集合
		Set<String> permissions = new HashSet<>();
		//Module资源集合
		try{
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户Module资源信息" ,userDTO.getUserId());
			Set<ModulePermissionDTO> modulePermissionDTOs = iSecurityService.queryModulePermissionByUserId(userDTO.getUserId());
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 成功查询当前角色用户Module资源信息" ,userDTO.getUserId());
			for(ModulePermissionDTO modulePermissionDTO : modulePermissionDTOs){
				StringBuffer sb = new StringBuffer();
				sb.append(modulePermissionDTO.getAppCode());
				sb.append(":");
				sb.append(modulePermissionDTO.getModuleCode());
				permissions.add(sb.toString());
			}
		}catch (Exception e){
			logger.warn("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户Module资源信息,发生异常" ,userDTO.getUserId());
			throw new AccountException(e);
		}

		try{
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户operation资源信息" ,userDTO.getUserId());
			//operation资源集合
			Set<OperationPermissionDTO> operationPermissionDTOs = iSecurityService.queryOperationPermissionByUserId(userDTO.getUserId());
			logger.info("oid={},shiro - UserRealm.doGetAuthorizationInfo 成功查询当前角色用户operation资源信息" ,userDTO.getUserId());
			for(OperationPermissionDTO operationPermissionDTO : operationPermissionDTOs){
				StringBuffer sb = new StringBuffer();
				sb.append(operationPermissionDTO.getAppCode());
				sb.append(":");
				sb.append(operationPermissionDTO.getOperationCode());
				permissions.add(sb.toString());
			}
		}catch (Exception e){
			logger.warn("oid={},shiro - UserRealm.doGetAuthorizationInfo 查询当前用户operation资源信息,发生异常" ,userDTO.getUserId());
			throw new AccountException(e);
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	/**
	 * 验证用户身份方法
	 * @see AuthorizingRealm#doGetAuthenticationInfo(AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upTocken = (UsernamePasswordToken)token;
		String username = (String) upTocken.getPrincipal();
		if(null == this.iSecurityService)
			throw new AccountException("未初始化安全验证的 iSecurityService 服务");
		UserDTO userDTO = null;
		try{
			logger.info("oid={},shiro - UserRealm.doGetAuthenticationInfo 查询当前用户信息" ,username);
			userDTO = iSecurityService.findUserByLoginName(username);
			logger.info("oid={},shiro - UserRealm.doGetAuthenticationInfo 成功查询当前用户信息" ,username);
		}catch (Exception e){
			logger.warn("oid={},shiro - UserRealm.doGetAuthenticationInfo 查询当前用户信息,发生异常" ,username);
			throw new AccountException(e);
		}

		if (null == userDTO) {
			throw new UnknownAccountException("没有找到该账号");
		}
		/**
		 * 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		 */
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDTO.getLoginName(),userDTO.getPwd(), getName());
		return info;
	}

	public ISecurityService getiSecurityService() {
		return iSecurityService;
	}

	public void setiSecurityService(ISecurityService iSecurityService) {
		this.iSecurityService = iSecurityService;
	}
}
