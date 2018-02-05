/*
 * @com.ikongjian.uc.service.exception.BusinessException.java
 */
package com.my.common.util.exception;


import java.io.Serializable;

/**
 * 业务异常超类
 * @Project common
 * @version 1.0
 * @Author  cai
 * @Date    2016年8月12日
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = -6093528337682404785L;
	/**
	 * 异常编码.
	 */
	private int code = 0;

	private Object data;
	
    public BusinessException(Throwable cause) {
        super(cause);
    }
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
