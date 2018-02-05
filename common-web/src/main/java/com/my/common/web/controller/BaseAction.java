package com.my.common.web.controller;

import com.my.common.cache.RedisCache;
import com.my.common.util.exception.BusinessException;
import com.my.common.util.info.AppInfo;
import com.my.common.util.info.UserInfo;
import com.my.common.security.shiro.interFace.ISecurityService;
import com.my.common.util.GlobalUtil;
import com.my.common.util.JsonUtil;
import com.my.common.web.interFace.ISysService;
import com.my.common.web.vo.SysNodeVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 所有controller超类
 * @Project uc-web
 * @version 1.0
 * @Author  zhaoshaung
 * @author
 * @Date    2016年8月12日
 */
public class BaseAction {

	private static Logger logger = LoggerFactory.getLogger(BaseAction.class);

	@Autowired
	protected ISysService sysService;

	@Autowired
	protected ISecurityService securityService;


	protected ServiceResult createDefaultResult() {
		return new ServiceResult();
	}

	@ExceptionHandler(BusinessException.class)
	public @ResponseBody ServiceResult businessExceptionHandler(BusinessException e, HttpServletRequest request) {
		logger.error("BaseAction 捕捉到 BusinessException" ,e);
		e.printStackTrace();
		if(isAJAXP(request)){
			ServiceResult result = createDefaultResult();
			result.setCode(e.getCode());
			result.setMsg(e.getMessage());
			return result;
		}else {
			throw  e;
		}
	}

	@ExceptionHandler(RuntimeException.class)
    public @ResponseBody ServiceResult runtimeException(RuntimeException e, HttpServletRequest request)  throws Exception{
		logger.error("BaseAction 捕捉到 RuntimeException" ,e);
		e.printStackTrace();
		return commonHandler(e,request);
    }

	@ExceptionHandler(Exception.class)
	public @ResponseBody ServiceResult exceptionHandler(Exception e, HttpServletRequest request) throws Exception{
		logger.error("BaseAction 捕捉到 Exception" ,e);
		e.printStackTrace();
		return  commonHandler(e,request);
	}

	public ServiceResult commonHandler(Exception e, HttpServletRequest request) throws Exception{
		//如果是ajax请求
		if(isAJAXP(request)){
			ServiceResult result = createDefaultResult();
			result.setCode(1);
			result.setMsg( "系统异常");
			return result;
		}else{
			throw e;
		}
	}

	private boolean isAJAXP(HttpServletRequest request){
		return  request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString());
	}

	/**
	 * 获得post请求的参数
	 * @return
	 */
	public String getPostQueryString(HttpServletRequest request){
		String queryString = "";  
		Map<String, String[]> params = request.getParameterMap();  
        for (String key : params.keySet()) {  
            String[] values = params.get(key);  
            for (int i = 0; i < values.length; i++) {  
                String value = values[i];  
                queryString += key + "=" + value + "&";  
            }  
        }  
    	// 去掉最后一个空格  
		queryString = "".equals(queryString) ? "" : queryString.substring(0, queryString.length() - 1);
		return queryString;
	}

	/**
	 * 将menuTree提到base是因为兼顾两种情况
	 * 1、普通的pc端的分布式页面 ，页面用ajax请求，获取该数据在js处理
	 * 2、h5功能页面 ，在controller处理该数据 。
	 */
	protected List<SysNodeVo> getMenuTree(){
		String appCode = GlobalUtil.getCurrentSystemCode();
		Long userId = GlobalUtil.getCurrentUserInfo().getUserId();
		List<SysNodeVo> trees = null;
		try {
			String json = RedisCache.get("com:my:commons:sys:tree:"+userId+":"+appCode);
			if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(json))){
				trees = JsonUtil.parse(json,List.class,SysNodeVo.class);
			}
			if(trees!=null && trees.size()>0){
				return trees;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存到redis里,redis里没有再从数据库加载
		trees = sysService.getUserMenuTree(userId,appCode);
		if(trees!=null && trees.size()>0){
			try {
				RedisCache.set("com:my:commons:sys:tree:"+userId,JsonUtil.json(trees)+":"+appCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return trees;
	}

	@ModelAttribute("apps")
	protected List<AppInfo> getApps(){
		UserInfo userInfo = GlobalUtil.getCurrentUserInfo();
		List<AppInfo> apps = null;
		if(userInfo!=null) {
			apps = securityService.getAccessAppByUser(userInfo.getUserId());
		}else{
			apps = new ArrayList<>();
		}
		return apps;
	}
}
