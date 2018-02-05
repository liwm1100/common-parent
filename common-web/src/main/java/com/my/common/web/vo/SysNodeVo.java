/*
 * @com.ikongjian.uc.vo.TreeNode.java
 */
package com.my.common.web.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统节点页面封装类
 * @Project common
 * @version 1.0
 * @Author  zhaoshuang
 * @Date    2016年8月10日
 * @Describe 树节点
 */
public class SysNodeVo implements Serializable{
    private String id; // 分类ID

    private String name; // 分类名称
    
    private String pId; // 父分类ID
    
    private String code;  // 编码
    
    private boolean open = false; // 默认关闭节点
    
    private List<SysNodeVo> children; // 子类
    
    private Map<String, Object> attributes = new HashMap<String, Object>();
 	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<SysNodeVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysNodeVo> children) {
		this.children = children;
	}

	public void addAttribute(String key, Object val) {
		attributes.put(key, val);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

    
}
