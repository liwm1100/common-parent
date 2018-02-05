package com.my.common.util.context;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.my.common.util.info.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * dubbo调用上下文传递-服务提供端类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class ProviderContextFilter extends AbstractContextFilter {
    private static Logger logger = LoggerFactory.getLogger(ProviderContextFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Map<String,String> atts = invocation.getAttachments();
            String companyCode = atts.get(ProviderContextFilter.COMPANY_CODE_ATTACHMENT_KEY);
            if(null == companyCode){
                logger.debug("ProviderContextFilter 无法正常解析获得companyCode信息");
            }else{
                ContextVariable.setCompanyCode(companyCode);
            }

            String userInfoJson = atts.get(ProviderContextFilter.USER_INFO_ATTACHMENT_KEY);
            if(StringUtils.isBlank(userInfoJson)){
                logger.debug("ProviderContextFilter 无法正常解析获得UserInfo信息");
            }else{
                try {
                    UserInfo userInfo = JSON.parse(userInfoJson, UserInfo.class);
                    ContextVariable.setUserInfo(userInfo);
                } catch (ParseException e) {
                    logger.error("ProviderContextFilter 无法正常反序列化UserInfo信息" ,e);
                }
            }

            String currenthttpUrl = atts.get(ProviderContextFilter.CURRENT_HTTP_REQUEST_URL);
            if(StringUtils.isBlank(currenthttpUrl)){
                logger.debug("ProviderContextFilter 无法正常解析获得 CURRENT_HTTP_REQUEST_URL 信息"); //级别低
            }else {
                ContextVariable.setCurrentRequestURL(currenthttpUrl);
            }

            String currentHttpSysCode = atts.get(ProviderContextFilter.CURRENT_HTTP_REQUEST_SYSTEM_CODE);
            if(StringUtils.isBlank(currentHttpSysCode)){
                logger.debug("ProviderContextFilter 无法正常解析获得 CURRENT_HTTP_REQUEST_SYSTEM_CODE 信息");//级别低
            }else{
                ContextVariable.setCurrentRequestSysCode(currentHttpSysCode);
            }
            return invoker.invoke(invocation);
        } finally {
            try {
                ContextVariable.clearCompanyCode();
            } catch (Exception e) {
                logger.error("ProviderContextFilter ,执行 ContextVariable.clearCompanyCode() 出错" ,e);
            }
            try {
                ContextVariable.clearUserInfo();
            } catch (Exception e) {
                logger.error("ProviderContextFilter ,执行 ContextVariable.clearUserInfo() 出错" ,e);
            }

            try {
                ContextVariable.clearCurrentRequestSysCode();
            } catch (Exception e) {
                logger.error("ProviderContextFilter ,执行 ContextVariable.clearCurrentRequestSysCode() 出错" ,e);
            }

            try {
                ContextVariable.clearCurrentRequestURL();
            } catch (Exception e) {
                logger.error("ProviderContextFilter ,执行 ContextVariable.clearCurrentRequestURL() 出错" ,e);
            }
        }
    }
}
