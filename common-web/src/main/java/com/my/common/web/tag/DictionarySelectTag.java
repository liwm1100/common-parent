package com.my.common.web.tag;

import com.my.common.util.SpringContextUtil;
import com.my.common.web.interFace.IDictionarySelectService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

/**
 * 数据字典标签 ，freemarker标签
 * @author
 * @version 1.0
 * @project common
 * @date 2016年11月1日
 * @description
 */
public class DictionarySelectTag implements TemplateDirectiveModel {

    private static Logger logger = LoggerFactory.getLogger(DictionarySelectTag.class);
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        try {
            exec(environment,map,templateModels,templateDirectiveBody);
        }catch (Exception e ){
            logger.error("解析freemarker - has_permission标签，发生异常" ,e);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param environment
     * @param map
     * @param templateModels
     * @param templateDirectiveBody
     * @throws TemplateException
     * @throws IOException
     */

    private  void exec(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        SimpleScalar typeScalar = (SimpleScalar) map.get("type");  //输出类型 ，默认为select; 也可以是input
        SimpleScalar valueScalar= (SimpleScalar) map.get("value");  //
        SimpleScalar codeScalar = (SimpleScalar) map.get("code");  //对应的dict编码 ，不能为空
        SimpleScalar nameScalar = (SimpleScalar) map.get("name");
        if (null == codeScalar || codeScalar.getAsString().equals("")) {
            throw new RuntimeException("DictionarySelectTag  标签，需要指定 code");
        }
        SimpleScalar idScalar = (SimpleScalar) map.get("id");
        SimpleScalar classScalar = (SimpleScalar) map.get("class");
        SimpleScalar styleScalar = (SimpleScalar) map.get("style");

        String type = (null == typeScalar || !(typeScalar.getAsString().equals("select") || typeScalar.getAsString().equals("input") || typeScalar.getAsString().equals("text"))) ? "select":typeScalar.getAsString();
        String value = (null == valueScalar || valueScalar.getAsString().equals(""))? null : valueScalar.getAsString();
        String code = codeScalar.getAsString();
        String name = null == nameScalar?"":nameScalar.getAsString();
        String id = null == idScalar? "" :idScalar.getAsString();
        String cls = null == classScalar? "":classScalar.getAsString();
        String style = null == styleScalar?"":styleScalar.getAsString();

        String body = null;
        switch (type){
            case "text" :
                body = generateText(value,code);
                break;
            case "input" :
                body = generateInput(value,code,name,id,cls,style);
                break;
             default:
                 body = generateSelect(value,code,name,id,cls,style);
                 break;
        }
        Writer out = environment.getOut();
        out.write(body);
    }

    private String generateText(String value ,String code){
        IDictionarySelectService dictionarySelectService  =  SpringContextUtil.getBean(IDictionarySelectService.class);
        if(null == dictionarySelectService){
            throw new RuntimeException("需要引入IDictionarySelectService.class 的dubbo服务");
        }
        try {
            CommonDictionary dictionary = dictionarySelectService.queryCommonDictionaryInfo(code ,value);
            if(null == dictionary){
                return "";
            }else{
                return dictionary.getName();
            }
        } catch (Exception e) {
            logger.error("生成dict-select时,发生异常" ,e);
            throw new RuntimeException(e);
        }
    }

    private String generateSelect(String value ,String code ,String name ,String id ,String cls ,String style){
        StringBuffer sb = new StringBuffer();
        IDictionarySelectService dictionarySelectService  =  SpringContextUtil.getBean(IDictionarySelectService.class);
        if(null == dictionarySelectService){
            throw new RuntimeException("需要引入IDictionarySelectService.class 的dubbo服务");
        }
        try {
            List<CommonDictionary> commonDictionarys = dictionarySelectService.queryCommonDictionaryInfos(code);
            sb.append("<select name=\"");
            sb.append( name);
            sb.append("\" ");

            sb.append("id=\"");
            sb.append(id);
            sb.append("\" ");

            sb.append("class=\"");
            sb.append(cls);
            sb.append("\" ");

            sb.append("style=\"");
            sb.append(style);
            sb.append("\" ");

            sb.append("><option value=\"\">请选择</option>");
            for(CommonDictionary dictionary : commonDictionarys){
                sb.append("<option  value=\"");
                sb.append(dictionary.getCode());
                sb.append("\" ");

                if(value!= null && value.equals(dictionary.getCode())){
                    sb.append("selected=\"selected\" ");
                }
                sb.append(">");
                sb.append(dictionary.getName());
                sb.append("</option>");

            }
            sb.append("</select>");
            return sb.toString();
        } catch (Exception e) {
            logger.error("生成dict-select时,发生异常" ,e);
            throw new RuntimeException(e);
        }
    }

    private String generateInput(String value ,String code ,String name ,String id ,String cls ,String style){
        StringBuffer sb = new StringBuffer();
        IDictionarySelectService dictionarySelectService  =  SpringContextUtil.getBean(IDictionarySelectService.class);
        if(null == dictionarySelectService){
            throw new RuntimeException("需要引入IDictionarySelectService.class 的dubbo服务");
        }
        try {
            CommonDictionary dictionary = dictionarySelectService.queryCommonDictionaryInfo(code ,value);
            if(null == dictionary){
                sb.append("<input name=\"");
                sb.append(name);
                sb.append("\" ");

                sb.append("id=\"");
                sb.append(id);
                sb.append("\" ");

                sb.append("class=\"");
                sb.append(cls);
                sb.append("\" ");

                sb.append("style=\"");
                sb.append(style);
                sb.append("\" ");

                sb.append("type=\"text\" value=\"\" readonly=\"readonly\"></input>");

            }else{
                sb.append("<input name=\"");
                sb.append(name);
                sb.append("\" ");

                sb.append("id=\"");
                sb.append(id);
                sb.append("\" ");

                sb.append("class=\"");
                sb.append(cls);
                sb.append("\" ");

                sb.append("style=\"");
                sb.append(style);
                sb.append("\" ");

                sb.append("type=\"text\" value=\"");
                sb.append(dictionary.getName());
                sb.append("\" readonly=\"readonly\"></input>");

            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("生成dict-select时,发生异常" ,e);
            throw new RuntimeException(e);
        }
    }
}
