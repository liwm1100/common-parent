/*
 * @org.common.web.interFace.SysService.java
 */
package com.my.common.web.interFace;

import com.my.common.util.info.AppInfo;
import com.my.common.web.vo.SysNodeVo;

import java.util.List;

/**
 * 系统接口 - 具体实现又uc通过dubbo对外提供
 * @Project common-web
 * @version 1.0
 * @Author  zhaoshuang
 * @Date    2016年8月24日
 */
public interface ISysService {

	
	/**
	 * 获取用户菜单
	 * @param userId 用户ID
	 * @param appCode appcode
	 * @return List<SysNodeVo>
	 */
	public List<SysNodeVo> getUserMenuTree(Long userId, String appCode);

}
