package com.my.common.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * json序列化扩展类，负责将long数据序列化成字符串。以规避js环境下long 越界异常
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/3
 */
public class LongJacksonSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(gen, null == value? "0" :value.toString());

    }
}
