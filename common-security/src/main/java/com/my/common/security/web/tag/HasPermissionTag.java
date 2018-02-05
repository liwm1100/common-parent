package com.my.common.security.web.tag;

import com.my.common.util.GlobalUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 权限标签，freemarker标签 。实现自{@link TemplateDirectiveModel}
 * @author
 * @version 1.0
 * @project uc
 * @date 2016/9/5
 */
public class HasPermissionTag implements TemplateDirectiveModel {

    private static Logger logger = LoggerFactory.getLogger(HasPermissionTag.class);
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        try {
            exec(environment,map,templateModels,templateDirectiveBody);
        }catch (Exception e ){
            logger.error("解析freemarker - has_permission标签，发生异常" ,e);
            e.printStackTrace();
        }
    }

    private  void exec(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        SimpleScalar button = (SimpleScalar) map.get("button");
        if (null == button) {
            throw new RuntimeException("hasPermission 标签，需要指定button");
        }
        String appCode = GlobalUtil.getCurrentSystemCode();
        StringBuffer sb = new StringBuffer();
        sb.append(appCode);
        sb.append(":");
        sb.append(button.getAsString());
        if (SecurityUtils.getSubject().isPermitted(sb.toString())) {
            Writer out = environment.getOut();
            if (templateDirectiveBody != null) {
                templateDirectiveBody.render(out);
            }
        }
    }

}
