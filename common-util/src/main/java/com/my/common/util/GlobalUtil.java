package com.my.common.util;

import com.my.common.util.context.ContextVariable;
import com.my.common.util.inf.IBaseService;
import com.my.common.util.info.AppInfo;
import com.my.common.util.info.JobInfo;
import com.my.common.util.info.OrganizationInfo;
import com.my.common.util.info.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;


/**
 * 提供给全产品线的全局工具API类
 *
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/16
 */
public class GlobalUtil {
    private static AppInfo appInfo = null;
    private static final String QUERY_APPINFO_LOCK = new String("GlobalUtil.QUERY_APPINFO_LOCK");

    private GlobalUtil() {
    }

    /**
     * 获取当前登陆者的基本信息
     *
     * @return UserInfo ,or  {@Code null} if can not found in shiro session or global context
     */
    public static UserInfo getCurrentUserInfo() {
        UserInfo u = ContextVariable.getUserInfo();
        if (null == u) {
            try {
                Subject subject = SecurityUtils.getSubject();
                u = (UserInfo) subject.getSession(true).getAttribute("global-ikj-my-common-zn-getCurrentUserInfo");
            } catch (Exception e) {
                throw new IllegalStateException("未初始化当前登录人的相关属性");
            }
        }
        return null == u ? null : u.clone();
    }

    /**
     * 获取当前登录者的主要（专职）岗位信息
     *
     * @return {@link JobInfo}
     */
    public static JobInfo getMainJobByCurrentUser() {
        UserInfo u = getCurrentUserInfo();
        if (null == u)
            throw new IllegalStateException("未初始化当前登录人的相关属性");
        IBaseService iBaseService = SpringContextUtil.getBean(IBaseService.class);
        return iBaseService.selectMainJobByUserCode(u.getUserId());
    }


    /**
     * 获取当前登录者的所有岗位信息
     *
     * @return {@link JobInfo}
     */
    public static List<JobInfo> getJobsByCurrentUser() {
        UserInfo u = getCurrentUserInfo();
        if (null == u)
            throw new IllegalStateException("未初始化当前登录人的相关属性");
        IBaseService iBaseService = SpringContextUtil.getBean(IBaseService.class);
        return iBaseService.selectJobsByUserCode(u.getUserId());
    }

    /**
     * 获取当前登陆者的所属公司
     *
     * @return OrganizationInfo or {@Code null} if can not found in shiro session or global context
     */
    public static OrganizationInfo getCurrentCompanyInfo() {
        String code = getCurrentCompanyCode();
        IBaseService iBaseService = SpringContextUtil.getBean(IBaseService.class);
        if (null == iBaseService)
            throw new IllegalStateException("未初始化 IBaseService 相关服务");
        OrganizationInfo organizationInfo = iBaseService.selectOrganizationInfoByCode(code);
        return organizationInfo;
    }

    /**
     * 获得当前登录者所属组织机构公司编码
     *
     * @return
     */
    public static String getCurrentCompanyCode() {
        Object o = ContextVariable.getCompanyCode();
        if (null == o) {
            try {
                Session shiroSession = SecurityUtils.getSubject().getSession(true);
                o = shiroSession.getAttribute("global-ikj-my-common-zn-current-company-code");
            } catch (Exception e) {
                throw new IllegalStateException("系统未缓存当前登录者所属公司编码属性");
            }
        }
        if (null == o) {
            throw new IllegalStateException("系统未缓存当前登录者所属公司编码属性");
        } else {
            return o.toString();
        }
    }


    /**
     * 获取当前系统的基本信息
     *
     * @return AppInfo ,null if can not find
     */
    public static AppInfo getCurrentSystemInfo() {
        if (null == appInfo)
            synchronized (QUERY_APPINFO_LOCK) {
                if(null == appInfo)
                    appInfo = queryAppInof();
            }
        return (null == appInfo) ? null : appInfo.clone();
    }

    private static AppInfo queryAppInof() {
        String code = getCurrentSystemCode();
        if (null == code)
            throw new IllegalStateException("未配置 com.my.framework.app.name ");
        IBaseService iBaseService = SpringContextUtil.getBean(IBaseService.class);
        if (null == iBaseService)
            throw new IllegalStateException("未初始化 IBaseService 相关服务");
        return iBaseService.selectAppInfoBycode(code);
    }

    /**
     * 获取当前系统的编码
     *
     * @return app code ,null if not config in properties
     */
    public static String getCurrentSystemCode() {
        return FrameworkProperties.getCurrentAppName();
    }

}
