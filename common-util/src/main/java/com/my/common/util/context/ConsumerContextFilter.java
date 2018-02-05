package com.my.common.util.context;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.*;
import com.my.common.util.info.UserInfo;
import com.my.common.util.GlobalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * dubbo调用上下文传递-服务调用端类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class ConsumerContextFilter extends AbstractContextFilter {
    private static Logger logger = LoggerFactory.getLogger(ConsumerContextFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Map<String,String> attachments = invocation.getAttachments();
        Object userInfo = null ;
        try{ //官网体系 ，没有用户相关信息 需要屏蔽异常
             userInfo = GlobalUtil.getCurrentUserInfo();
        }catch (IllegalStateException e){}

        if(null == userInfo){
            logger.error("ConsumerContextFilter 无法正常解析获得UserInfo信息");
        }else{
            try {
                String userInfoJson = JSON.json(userInfo);
                attachments.put(ConsumerContextFilter.USER_INFO_ATTACHMENT_KEY,userInfoJson);
            } catch (IOException e) {
                logger.error("ConsumerContextFilter 无法正常序列化UserInfo信息" ,e);
            }
        }
        String companyCode = null;
        try {
            companyCode = GlobalUtil.getCurrentCompanyCode();
        } catch (Exception e) {
            //nothing to do
        }
        attachments.put(ConsumerContextFilter.COMPANY_CODE_ATTACHMENT_KEY ,companyCode);

        if( ContextVariable.getCurrentRequestURL() != null ){
            attachments.put(ConsumerContextFilter.CURRENT_HTTP_REQUEST_URL ,ContextVariable.getCurrentRequestURL());
        }else {
            logger.debug("ConsumerContextFilter 无法正常解析获得 CURRENT_HTTP_REQUEST_URL 信息");
        }

        if(ContextVariable.getCurrentRequestSysCode() != null){
            attachments.put(ConsumerContextFilter.CURRENT_HTTP_REQUEST_SYSTEM_CODE ,ContextVariable.getCurrentRequestSysCode());
        }else{
            logger.debug("ConsumerContextFilter 无法正常解析获得 CURRENT_HTTP_REQUEST_SYSTEM_CODE 信息");
        }
        return  invoker.invoke(invocation);
    }
}
