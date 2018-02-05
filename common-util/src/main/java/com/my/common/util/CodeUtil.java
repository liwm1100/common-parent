package com.my.common.util;

import java.text.DecimalFormat;

/**
 * 编码生成工具
 * @author huqilong
 * @version 1.0
 * @project common
 * @date 2016/9/3
 */
public class CodeUtil {

	public static String getOrgCode(Integer sequence){
		return format(sequence,"000000");
	}
	
	public static String getDeptCode(Integer sequence){
		return format(sequence,"000000");
	}
	
	public static String getRoleCode(Integer sequence){
		return format(sequence,"000000");
	}
	
	public static String getMoudleCode(Integer sequence){
		return format(sequence,"000000");
	}
	
	public static String getOperationCode(Integer sequence){
		return format(sequence,"000000");
	}

	public static String getUserCode(Integer sequence){
		return format(sequence,"00000000");
	}
	
	public static String format(Integer sequence,String format){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(sequence);
	}
}
