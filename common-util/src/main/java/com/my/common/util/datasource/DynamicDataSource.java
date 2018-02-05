package com.my.common.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源 ，继承自{@link AbstractRoutingDataSource}
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return JdbcContextHolder.getJdbcType();
	}
	
}