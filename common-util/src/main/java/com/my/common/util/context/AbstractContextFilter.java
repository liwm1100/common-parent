package com.my.common.util.context;

import com.alibaba.dubbo.rpc.Filter;

/**
 * dubbbo调用上下文传递的抽象类
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/21
 */
abstract class AbstractContextFilter implements Filter {
   static final String COMPANY_CODE_ATTACHMENT_KEY = "com.my.common.context.rpc.atta.company.code";
   static final String USER_INFO_ATTACHMENT_KEY = "com.my.common.context.rpc.atta.user.info";
   static final String CURRENT_HTTP_REQUEST_URL = "com.my.common.context.rpc.atta.http.request.url";
   static final String CURRENT_HTTP_REQUEST_SYSTEM_CODE = "com.my.common.context.rpc.atta.http.request.sys.code";
}
