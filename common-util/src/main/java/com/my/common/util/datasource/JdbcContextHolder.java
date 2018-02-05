package com.my.common.util.datasource;

/**
 * 动态数据源-上下文类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class JdbcContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setJdbcType(String jdbcType) {
		contextHolder.set(jdbcType);
	}

	public static String getJdbcType() {
		return (String) contextHolder.get();
	}

	public static void clearJdbcType() {
		contextHolder.remove();
	}
}
