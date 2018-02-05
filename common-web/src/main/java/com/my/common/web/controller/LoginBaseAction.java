package com.my.common.web.controller;

import com.my.common.util.info.AppInfo;
import com.my.common.util.info.OrganizationInfo;
import com.my.common.util.info.OrganizationJobInfo;
import com.my.common.util.info.UserInfo;
import com.my.common.util.GlobalUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

import static com.my.common.web.controller.LoginBaseAction.loginResult.error1;
import static com.my.common.web.controller.LoginBaseAction.loginResult.error2;
import static com.my.common.web.controller.LoginBaseAction.loginResult.success;
import static com.my.common.web.controller.LoginBaseAction.loginResult.*;

/**
 * @version 1.0
 * @Project common
 * @Author
 * @Date 2017-03-21
 */
public abstract class LoginBaseAction {
    private static Logger logger = LoggerFactory.getLogger(LoginBaseAction.class);

    public enum loginResult{

        error1("用户名/密码错误"),
        success("成功"),
        error2("其他错误：请联系系统管理人员");

        private String message;
        loginResult(String message){
            this.message = message;
        }

        public String getMessage(){
            return this.message;
        }

    }

    public loginResult doLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return error1;
        }

        String error = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //记住我
        token.setRememberMe(true);
        try{
            subject.login(token);
            this.cacheUserInfo(username,request.getSession());
            this.cacheTopBarInfo(request);
            //this.cacheInfo(request,response);
            return success;
        }catch (UnknownAccountException e){
            return error1;
        }catch (IncorrectCredentialsException e){
            return error1;
        }catch (Exception e){
            logger.error("oid="+username+",登录时发生未知错误" ,e);
            return error2;
        }
    }


    /**
     * 缓存logout 链接及app bar
     * @param session
     * @throws Exception
     */
    private void cacheTopBarInfo(HttpServletRequest request) throws Exception{
        AppInfo ucApp = null;
        List<AppInfo> apps  = getAccessAppByCurrentUser();
        HttpSession session = request.getSession();
        session.setAttribute("http-ikj-my-common-zn-access-apps" ,apps);
        session.setAttribute("http-ikj-my-common-zn-current-user-info" ,GlobalUtil.getCurrentUserInfo());
        ucApp = getAppInfoByCode("uc");
        if(ucApp != null) {
            String logoutUrl = null;
            int port = request.getServerPort();
            if (StringUtils.endsWith(ucApp.getDomain(), "/")) {
                logoutUrl = ucApp.getDomain() +":"+ port + "logout";
            } else {
                logoutUrl = ucApp.getDomain() +":"+ port + "/logout";
            }
            session.setAttribute("http-ikj-my-common-zn-system-logout-url", logoutUrl);
        }
    }

    private void cacheUserInfo(String loginName,HttpSession httpSession) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Session shiroSession = subject.getSession(true);
        UserInfo u =this.getUserInfo(loginName);
        if(null != u ){
            //把用户信息保存在全局session里
            shiroSession.setAttribute("global-ikj-my-common-zn-getCurrentUserInfo", u);
            shiroSession.setAttribute("global-ikj-my-common-zn-current-company-code", u.getStoreNo());
        }
    }

    /**
     *  根据组织变化，算出所属公司编码
     * @param organizationCode org-code
     * @return code
     */
    private String getCompanyCode(String organizationCode){
        String[] codes = StringUtils.split(organizationCode ,"/");
        if(codes.length > 1){
            return codes[1];
        }else{
            return codes[0];
        }
    }




    protected abstract UserInfo getUserInfo(String loginName) throws Exception ;
    protected abstract List<OrganizationJobInfo> getCurrentUserOrganizationJobInfos() throws Exception;
    protected abstract List<OrganizationInfo> getOrganizationInfos (Set<String> codes) throws Exception;
    protected abstract AppInfo getAppInfoByCode(String appCode) throws  Exception;
    protected abstract List<AppInfo> getAccessAppByCurrentUser() throws  Exception;
    protected abstract void cacheInfo(HttpServletRequest request ,HttpServletResponse response) throws Exception;
}
