package com.my.common.security.shiro.filter;

import com.my.common.util.info.AppInfo;
import com.my.common.security.shiro.interFace.ISecurityService;
import com.my.common.util.GlobalUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限过滤filter
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class AuthorityFilter extends AccessControlFilter{

	private static Logger logger = LoggerFactory.getLogger(AuthorityFilter.class);

	private String unauthorizedURL ;
	private ISecurityService iSecurityService;

	public String getUnauthorizedURL() {
		return unauthorizedURL;
	}

	public void setUnauthorizedURL(String unauthorizedURL) {
		this.unauthorizedURL = unauthorizedURL;
	}

	public ISecurityService getiSecurityService() {
		return iSecurityService;
	}

	public void setiSecurityService(ISecurityService iSecurityService) {
		this.iSecurityService = iSecurityService;
	}

	/**
	 * @see AccessControlFilter#isAccessAllowed(ServletRequest, ServletResponse, Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		String url = WebUtils.getPathWithinApplication((HttpServletRequest) request);
		AppInfo appInfo = GlobalUtil.getCurrentSystemInfo();
		//是否忽略权限验证
		if(iSecurityService.isIgnoredUrl(url ,appInfo.getAppId())){
			return true;
		}
		String permission = null;
		try{
			permission = iSecurityService.queryPermissionByURL(url,appInfo.getCode());
		}catch (Throwable e){
			logger.error("oid="+url+"-"+appInfo.getCode()+",shiro - AuthorityFilter.isAccessAllowed 向UC当前url对应的permission,发生异常" ,e);
			e.printStackTrace();
			return false;
		}
		Subject subject = SecurityUtils.getSubject();
		if(null == permission){
			logger.info("oid={}-{},shiro - AuthorityFilter.isAccessAllowed 无法找到对应的 permission" ,url ,appInfo.getCode());
			return false;
		}
		return subject.isPermitted(permission);
	}

	/**
	 * @see AccessControlFilter#onAccessDenied(ServletRequest, ServletResponse)
	 * */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		WebUtils.redirectToSavedRequest(request, response, unauthorizedURL);
		return false;
	}

}
