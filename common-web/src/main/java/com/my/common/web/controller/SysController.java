/*
 * @org.common.web.controller.MainController.java
 */
package com.my.common.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.common.security.shiro.interFace.ISecurityService;
import com.my.common.util.GlobalUtil;
import com.my.common.web.interFace.ISysService;
import com.my.common.web.vo.SysNodeVo;

/**
 * 平台级controller
 * @Project common
 * @version 1.0
 * @Author  zhaoshuang
 * @Date    2016年8月24日
 */

@Controller
public class SysController extends BaseAction{
	private static Logger logger = LoggerFactory.getLogger(SysController.class);

	@Autowired
	private ISecurityService securityService;

	public static class Result{
		private int state;
		private String msg;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		private static Result generateErrorResult(String msg){
			Result result = new Result();
			result.setState(0);
			result.setMsg(msg);
			return  result;
		}

		private static Result generateSucessResult(){
			Result result = new Result();
			result.setState(1);
			return  result;
		}
	}

	/**
	 * head页面修改所属公司
	 * @param selectedCode 选中公司编码
	 * @return 结果
	 * @author
	 * @date 2016年8月26日
	 */
	@RequestMapping("/changeCompany")
	@ResponseBody
	public Result changeCompany(@RequestParam(value = "code" ,defaultValue = "" ) String selectedCode){
		if(StringUtils.isBlank(selectedCode)){
			return Result.generateErrorResult("数据不正确，请重新刷新页面再尝试");
		}
		try {
			selectedCode = Base64.decodeToString(selectedCode.getBytes("UTF-8"));
			Session shiroSession = SecurityUtils.getSubject().getSession(true);
			shiroSession.setAttribute("global-ikj-my-common-zn-current-company-code" ,selectedCode);
			return Result.generateSucessResult();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Result.generateErrorResult("服务端异常，请重试或联系管理员");
		}
	}

	/**
	 * head页面修改密码
	 * @param oldPwd  旧密码
	 * @param newPwd1 新密码1
	 * @param newPwd2 新密码2
	 * @return 结果
	 * @author
	 * @date 2016年8月26日
	 */
	@RequestMapping("/editpwd")
	@ResponseBody
	public Result editPwd(@RequestParam(value = "c" ,defaultValue = "") String oldPwd
			,@RequestParam(value ="b" ,defaultValue = "") String newPwd1
			,@RequestParam(value ="a",defaultValue = "") String newPwd2){

		if(StringUtils.isBlank(oldPwd)
				|| StringUtils.isBlank(newPwd1)
				|| StringUtils.isBlank(newPwd2)){
			return  Result.generateErrorResult("请将输入框填满");
		}

		try {
			oldPwd = Base64.decodeToString(oldPwd.getBytes("UTF-8"));
			newPwd1 = Base64.decodeToString(newPwd1.getBytes("UTF-8"));
			newPwd2 = Base64.decodeToString(newPwd2.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("oid="+GlobalUtil.getCurrentUserInfo().getUserId()+",修改用户密码 解码时是发生错误 " ,e);
			e.printStackTrace();
			return  Result.generateErrorResult("服务端发生异常，请联系系统管理员");
		}

		if(!newPwd1.equals(newPwd2)){
			return Result.generateErrorResult("新密码两次输入不一致");
		}

		if(newPwd1.equals(oldPwd)){
			return  Result.generateErrorResult("新旧密码一样");
		}

		try{
			long id = GlobalUtil.getCurrentUserInfo().getUserId() ;
			logger.info("oid={},调用securityService.matchPwd 验证密码,start" ,id);
			if(!securityService.matchPwd(oldPwd , id)){
				return  Result.generateErrorResult("旧密码输入不正确");
			}
			logger.info("oid={},调用securityService.matchPwd 验证密码,end" ,id);
			logger.info("oid={},调用securityService.setPwd 修改密码,start" ,id);
			securityService.setPwd(newPwd1 ,id);
			logger.info("oid={},调用securityService.setPwd 修改密码,end" ,id);
			return Result.generateSucessResult();
		}catch (Exception e){
			logger.error("oid="+GlobalUtil.getCurrentUserInfo().getUserId()+",修改用户密码是发生错误" ,e);
			return  Result.generateErrorResult("服务端发生异常，请联系系统管理员");
		}
	}

	/**
	 * 获得系统菜单树
	 * @return
	 */
	@RequestMapping("/sysMenuTree")
	@ResponseBody
	public List<SysNodeVo> sysMenuTree(HttpServletRequest request,
            HttpServletResponse response) {
		return getMenuTree();
	}


}
