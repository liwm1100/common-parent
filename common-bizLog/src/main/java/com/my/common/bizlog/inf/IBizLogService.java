package com.my.common.bizlog.inf;

import java.util.Date;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.my.common.bizlog.ActionEnum;
import com.my.common.bizlog.model.BizLog;

/**
 * 业务日志查询接口，该接口的实现由UC项目通过dubbo对外提供
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/26
 */
public interface IBizLogService {

	/**
	 * 页查询 业务日志 （不需要的过滤条件，传入null即可）
	 * @param pageNum  页码
	 * @param pageSize 每页数量
	 * @param bizType 业务类型
	 * @param bizCode 业务编码/id
	 * @param actionEnum 行为枚举
	 * @param startDateTime 开始日期
	 * @param endDateTime 结束时间
	 * @return 查询结果列表
	 */
	public PageInfo<BizLog> getBizLogPage(int pageNum, int pageSize,
										  String bizType, String bizCode,
										  ActionEnum actionEnum, String operateUserId, String operateUserName,
										  Date startDateTime, Date endDateTime, String httpRequestURL);

	/**
	 * 查询单个业务日志信息
	 * @param bizLogId 业务日志id
	 * @return 业务日志信息
	 * @author
	 * @date 2016/9/26
	 */
	public BizLog selectByPrimaryKey(Long bizLogId);
}
