package com.my.common.util.datasource;


import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 动态数据源aop切片类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class DataSourceAop {

	public static Logger logger = LoggerFactory.getLogger(DataSourceAop.class);
	
    private void dataSourcePointcut() {
    }

    public void preServiceMethod(JoinPoint jp) throws Exception {
        String methodName = jp.getSignature().getName();
        String jdbcType = getJdbcType( methodName);
        JdbcContextHolder.setJdbcType(jdbcType);
    }

    private String getJdbcType( String methodName) {
         String methodRegex = "^(add|save|insert|insertBatch|batchAdd|edit|set|update|batchUpdate|merge|remove|delete|batchDelete|batchRemove).*";
        //读写分离
        if (methodName.matches(methodRegex)) {
            return  "Master";
        } else {
            return "Slave";
        }
    }
}
