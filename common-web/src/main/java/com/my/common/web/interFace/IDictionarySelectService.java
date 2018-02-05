package com.my.common.web.interFace;

import com.my.common.web.tag.CommonDictionary;

import java.util.List;

/**
 * 全局数据字典接口 -- 具体实现由hades项目经dubbo对外提供
 * @author
 * @version 1.0
 * @project common
 * @date 2016/11/1
 */
public interface IDictionarySelectService {
    CommonDictionary queryCommonDictionaryInfo(String typeCode, String infoCode) throws Exception;
    List<CommonDictionary> queryCommonDictionaryInfos(String typeCode) throws Exception;
    CommonDictionary queryCommonDictionaryType(String typeCode) throws Exception;
}
