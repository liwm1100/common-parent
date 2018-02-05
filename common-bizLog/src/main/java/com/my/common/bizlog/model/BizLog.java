package com.my.common.bizlog.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.my.common.bizlog.ActionEnum;
import com.my.common.util.json.LongJacksonDeserializer;
import com.my.common.util.json.LongJacksonSerializer;
import com.my.common.util.JsonUtil;

public class BizLog implements Serializable {
    private static final long serialVersionUID = -7563147313801640821L;
	@JsonSerialize(using= LongJacksonSerializer.class)
    @JsonDeserialize(using = LongJacksonDeserializer.class)
    //业务日志id
    private Long bizLogId;
    //记录业务日志的埋点所属的系统编码
    private String appCode;
    /** 记录业务日志的埋点所属的系统名称*/
    private String appName;
    /** 业务行为*/
    private ActionEnum actionType;
    /** 业务编码 code或id*/
    private String bizCode;
    /** 业务类型*/
    private String bizType;
    /** 操作人id*/
    private Long oprUserId;
    /** 操作人名称*/
    private String oprUserName;
    /** 操作人日期*/
    private Date oprDate;
    /** 业务数据*/
    private String bizData;
    /** 请求的http url*/
    private String httpRequestURL;
    /** 请求的http 所属系统编码*/
    private String httpAppCode;
    /** 请求的http 所属系统名称*/
    private String httpAppName;
    /** 请求的http 对应的模块名称*/
    private String httpModuleName;
    /** 请求的http 对应的操作名称*/
    private String httpOperationName;

    public Long getBizLogId() {
        return bizLogId;
    }

    public void setBizLogId(Long bizLogId) {
        this.bizLogId = bizLogId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ActionEnum getActionType() {
        return actionType;
    }

    public void setActionType(ActionEnum actionType) {
        this.actionType = actionType;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Long getOprUserId() {
        return oprUserId;
    }

    public void setOprUserId(Long oprUserId) {
        this.oprUserId = oprUserId;
    }

    public String getOprUserName() {
        return oprUserName;
    }

    public void setOprUserName(String oprUserName) {
        this.oprUserName = oprUserName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getOprDate() {
        return (Date) oprDate.clone();
    }

    public void setOprDate(Date oprDate) {
        this.oprDate = (Date) oprDate.clone();
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    public String getHttpRequestURL() {
        return httpRequestURL;
    }

    public void setHttpRequestURL(String httpRequestURL) {
        this.httpRequestURL = httpRequestURL;
    }

    public String getHttpAppCode() {
        return httpAppCode;
    }

    public void setHttpAppCode(String httpAppCode) {
        this.httpAppCode = httpAppCode;
    }

    public String getHttpAppName() {
        return httpAppName;
    }

    public void setHttpAppName(String httpAppName) {
        this.httpAppName = httpAppName;
    }

    public String getHttpModuleName() {
        return httpModuleName;
    }

    public void setHttpModuleName(String httpModuleName) {
        this.httpModuleName = httpModuleName;
    }

    public String getHttpOperationName() {
        return httpOperationName;
    }


    public void setHttpOperationName(String httpOperationName) {
        this.httpOperationName = httpOperationName;
    }

    public <T> T getBizObject(Class<T> cls){
        return JsonUtil.parse(bizData ,cls);
    }
}