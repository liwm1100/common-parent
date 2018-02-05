package com.my.common.web.controller;

import com.my.common.util.exception.BusinessException;
import com.my.common.security.shiro.interFace.ISecurityService;
import com.my.common.web.interFace.ISysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 所有controller超类
 * @Project uc-web
 * @version 1.0
 * @Author  zhaoshaung
 * @author
 * @Date    2016年8月12日
 */
public class H5BaseAction {

	private static Logger logger = LoggerFactory.getLogger(H5BaseAction.class);

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

}
