package com.my.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json 序列化及反序列化工具
 * @Project common
 * @version 1.0
 * @Author  cai
 * @Date    2016年10月17日
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static ObjectMapper objectMapper2 = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		objectMapper2.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper2.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}

	public static String json(Object obj) {
		return json(obj, false);
	}

	public static String json(Object obj, boolean ignoreNullField) {
		try {
			if (ignoreNullField) {
				return objectMapper.writeValueAsString(obj);
			} else {
				return objectMapper2.writeValueAsString(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException("failed translate object to json", e);
		}
	}

	public static <T> T parse(String json, Class<T> type) {
		if(null == json)
			throw new IllegalArgumentException("json 不能为空");
		try {
			return objectMapper.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException("failed read object from json", e);
		}
	}

	public static <T> T parse(JsonNode jsonNode, Class<T> type) {
		try {
			return objectMapper.readValue(json(jsonNode), type);
		} catch (Exception e) {
			throw new RuntimeException("failed read object from json", e);
		}
	}

	public static <T> List<T> parse(String json, Class<?> listClass, Class<T> typeClass) {
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(listClass, typeClass);
		try {
			return objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			throw new RuntimeException("failed read object from json", e);
		}
	}
	
	public static Object parse(String json, JavaType javaType) {
		try {
			return objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			throw new RuntimeException("failed read object from json", e);
		}
	}

	
	public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
		return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	public static JavaType constructParametricType(Class<?> parametrized, JavaType... parameterClasses) {
		return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}
	
}
