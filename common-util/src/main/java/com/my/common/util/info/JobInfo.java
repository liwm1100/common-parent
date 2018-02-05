package com.my.common.util.info;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.my.common.util.json.LongJacksonDeserializer;
import com.my.common.util.json.LongJacksonSerializer;

import java.io.Serializable;

/**
 * @version 1.0
 * @Project common
 * @Author
 * @Date 2017-02-25
 */
public class JobInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 8462477047459428956L;

    @JsonSerialize(using = LongJacksonSerializer.class)
    @JsonDeserialize(using = LongJacksonDeserializer.class)
    private Long jobId;

    @JsonSerialize(using = LongJacksonSerializer.class)
    @JsonDeserialize(using = LongJacksonDeserializer.class)
    private Long organizationId;

    private String name;

    private String code;

    private Boolean isManager;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    @Override
    public JobInfo clone() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobId(this.getJobId());
        jobInfo.setCode(this.getCode());
        jobInfo.setManager(this.getManager());
        jobInfo.setName(this.getName());
        jobInfo.setOrganizationId(this.getOrganizationId());
        return jobInfo;
    }
}
