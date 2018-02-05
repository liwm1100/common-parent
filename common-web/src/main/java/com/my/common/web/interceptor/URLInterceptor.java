package com.my.common.web.interceptor;

import com.my.common.util.context.ContextVariable;
import com.my.common.util.FrameworkProperties;
import com.my.common.util.GlobalUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL拦截器 ，用于拦截请求获取请求的url
 * @version 1.0
 * @Project common
 * @Author
 * @Date 2016-12-06
 */
public class URLInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getServletPath();
        ContextVariable.setCurrentRequestURL(url);
        ContextVariable.setCurrentRequestSysCode(GlobalUtil.getCurrentSystemCode());
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        ContextVariable.clearCurrentRequestURL();
        ContextVariable.clearCurrentRequestSysCode();
    }

}
