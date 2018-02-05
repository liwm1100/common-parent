package com.my.common.bizlog;

import com.my.common.bizlog.model.BizLog;
import com.my.common.util.context.ContextVariable;
import com.my.common.util.id.IdGenerator;
import com.my.common.util.info.AppInfo;
import com.my.common.util.info.UserInfo;
import com.my.common.util.GlobalUtil;
import com.my.common.util.JsonUtil;
import com.my.common.util.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Date;

/**
 * 业务日志，埋点接口
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/26
 *
 */
public class BizLogger {
    /**
     * 埋点方法
     * @param action 业务行为
     * @param bizType 业务类型
     * @param bizCode 业务编码
     * @param bizData 业务数据
     * @author
     * @date 2016/9/26
     */
    public static void log(ActionEnum action ,String bizType,String bizCode ,String bizData){
        //check
        if(null == action)
            throw new IllegalArgumentException("action 不能为空");
        if(StringUtils.isBlank(bizType))
            throw new IllegalArgumentException("bizType 不能为空");
        if(StringUtils.isBlank(bizCode))
            throw new IllegalArgumentException("bizCode 不能为空");

        BizLog bizLog = new BizLog();
        bizLog.setBizLogId(IdGenerator.generate());
        bizLog.setActionType(action);
        bizLog.setBizCode(bizCode);
        bizLog.setBizType(bizType);
        bizLog.setBizData(bizData);
        bizLog.setOprDate(new Date());
        bizLog.setHttpAppCode(ContextVariable.getCurrentRequestSysCode());
        bizLog.setHttpRequestURL(ContextVariable.getCurrentRequestURL());

        AppInfo appInfo = GlobalUtil.getCurrentSystemInfo();
        if(null == appInfo){
            throw new IllegalStateException("无法获得当前系统的AppInfo ,请按照要求构建相关系统");
        }
        bizLog.setAppCode(appInfo.getCode());
        bizLog.setAppName(appInfo.getName());

        UserInfo userInfo = GlobalUtil.getCurrentUserInfo();
        if(null == userInfo)
            throw new IllegalStateException("无法获得当前系统的UserInfo ,请按照要求构建相关系统");
        bizLog.setOprUserId(userInfo.getUserId());
        bizLog.setOprUserName(userInfo.getUserName());

        AmqpTemplate template = (AmqpTemplate)SpringContextUtil.getBean("bizLogQueueTemplate");
        template.convertAndSend("common_bizlog_queue_key" ,bizLog);
    }

    public static void log(ActionEnum action ,String bizType,String bizCode ,Object bizObj){
        if(null == bizObj)
            throw new IllegalArgumentException("bizObj 不能为空");
        BizLogger.log(action,bizType,bizCode, JsonUtil.json(bizObj));
    }

}
