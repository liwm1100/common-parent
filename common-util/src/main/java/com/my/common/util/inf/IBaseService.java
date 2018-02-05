package com.my.common.util.inf;

import com.my.common.util.info.AppInfo;
import com.my.common.util.info.JobInfo;
import com.my.common.util.info.OrganizationInfo;

import java.util.List;

/**
 * 平台基础服务接口 - 具体实现由uc通过dubbo提供
 * @author
 * @version 1.0
 * @project common
 * @date 2016/8/30
 */
public interface IBaseService {
    AppInfo selectAppInfoBycode(String code);
    OrganizationInfo selectOrganizationInfoByCode(String Code);
    JobInfo selectMainJobByUserCode(long userId);
    List<JobInfo> selectJobsByUserCode(long UserId);
}
