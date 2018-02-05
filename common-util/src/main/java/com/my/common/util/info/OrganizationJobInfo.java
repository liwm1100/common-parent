package com.my.common.util.info;

import java.io.Serializable;

/**
 * @author
 * @version 1.0
 * @project uc
 * @date 2016/8/22
 * @description
 */
public class OrganizationJobInfo implements Serializable{

    private static final long serialVersionUID = -4746527035841114229L;
    private long organizaitonId ;
    private String organizationCode;
    private long organizationParent;
    private String organizaitonPath;
    private String organizationName;
    private long jobId;
    private String jobCode;
    private String jobName;
    private boolean isMain;

    public long getOrganizaitonId() {
        return organizaitonId;
    }

    public void setOrganizaitonId(long organizaitonId) {
        this.organizaitonId = organizaitonId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public long getOrganizationParent() {
        return organizationParent;
    }

    public void setOrganizationParent(long organizationParent) {
        this.organizationParent = organizationParent;
    }

    public String getOrganizaitonPath() {
        return organizaitonPath;
    }

    public void setOrganizaitonPath(String organizaitonPath) {
        this.organizaitonPath = organizaitonPath;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
