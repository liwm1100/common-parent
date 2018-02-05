package com.my.common.bizlog;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 业务日志业务行为枚举类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/26
 *
 */
public enum ActionEnum {
    CREATE(1, "创建"),
    UPDATE(2, "修改"),
    DELETE(3, "删除");

    private int actionCode;
    
    private String actionInfo;

    private ActionEnum(int actionCode, String actionInfo){
        this.actionCode = actionCode;
        this.actionInfo = actionInfo;
    }

    /**
     * 返回业务行为编码
     * @return 业务行为编码
     * @author
     * @date 2016/9/26
     */
    public int getActionCode(){
        return this.actionCode;
    }

    /**
     * 返回业务行为名称
     * @return 业务行为名称
     * @author
     * @date 2016/9/26
     */
	public String getActionInfo() {
		return actionInfo;
	}

    /**
     * 以map的方式返回业务行为类型
     * @return 以map的方式返回业务行为类型
     * @author zhaoshuang
     * @date 2016/9/26
     */
	public static Map<String, String> getAllStatus() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (ActionEnum obj : ActionEnum.values()) {
			map.put(obj.name(), obj.getActionInfo());
		}
		return map;
	}
}
