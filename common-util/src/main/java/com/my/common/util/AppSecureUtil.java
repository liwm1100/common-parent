package com.my.common.util;

/**
 * app加密验证算法
 * @author huqilong
 *
 */
public class AppSecureUtil {

	/**
	 * 验证app请求参数合法性
	 * @param jsonParam
	 * @return
	 */
	public static boolean valid(String jsonParam,String sign,String appKey,String secretKey){
		String computeSign = MD5.compute(appKey+jsonParam+secretKey);
		return sign.equals(computeSign);
	}
	
}
