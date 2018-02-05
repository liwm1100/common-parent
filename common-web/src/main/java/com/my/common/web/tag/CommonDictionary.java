package com.my.common.web.tag;

import java.io.Serializable;

/**
 * 通用数据字典封装类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/11/1
 */
public class CommonDictionary implements Serializable {
    private static final long serialVersionUID = 2044292244637277366L;
    private String name;
    private String code;
    private byte positon ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getPositon() {
        return positon;
    }

    public void setPositon(byte positon) {
        this.positon = positon;
    }
}