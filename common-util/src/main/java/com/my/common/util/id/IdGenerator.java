package com.my.common.util.id;

import com.robert.vesta.service.intf.IdService;
import com.my.common.util.SpringContextUtil;

/**
 * id生成器封装类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
public class IdGenerator {
    public static long generate(){
        IdService idService = (IdService) SpringContextUtil.getBean("idService");
        if(null != idService){
            return idService.genId();
        }else{
            throw new RuntimeException("未能初始化IdService");
        }
    }
}
