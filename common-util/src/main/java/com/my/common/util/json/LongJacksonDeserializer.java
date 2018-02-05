package com.my.common.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * json反序列化扩展类，负责将字符串反序列化成long。
 * @author
 * @version 1.0
 * @project common
 * @date 2016/10/17
 */
public class LongJacksonDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      return Long.parseLong( p.getText()) ;
    }
}
