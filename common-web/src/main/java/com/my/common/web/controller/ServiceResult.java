/*
 * @com.ikongjian.uc.service.util.ServiceResult.java
 */
package com.my.common.web.controller;


/**
 * ajax通用返回值
 * @Project common
 * @version 1.0
 * @Author  cai
 * @Date    2016年8月12日
 */
public class ServiceResult<T> {

	private Integer code;

	private String msg;

	private T result;

	public ServiceResult(){}

	public ServiceResult(int code ,String msg){
		this.code = code;
		this.msg = msg;
	}

	public ServiceResult(int code ,String msg ,T result){
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getResult() {
		return this.result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceResult [");
		if (code != null)
			builder.append("code=").append(code).append(", ");
		if (msg != null)
			builder.append("msg=").append(msg).append(", ");
		if (result != null)
			builder.append("result=").append(result);
		builder.append("]");
		return builder.toString();
	}

}