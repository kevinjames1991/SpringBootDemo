package com.mySBoot.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * Created by lanux on 2017/1/11.
 */
public class JsonUtil {

    private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * json 转 JsonNode
     *
     * @param jsonString
     * @return
     */
    public static JsonNode toJsonNode(String jsonString) {
        try {
            return mapper.readTree(jsonString);
        } catch (Exception e) {
            log.error(jsonString, e);
        }
        return null;
    }

    /**
     * 从json中抽取子json
     *
     * @param fieldName
     * @param jsonString
     * @return
     */
    public static JsonNode getJsonNode(String fieldName, String jsonString) {
        JsonNode jn = toJsonNode(jsonString);
        if (jn != null) {
            return jn.get(fieldName);
        }
        return null;
    }

    /**
     * 从json中抽取子json,转Object
     *
     * @param fieldName
     * @param jsonString
     * @return
     */
    public static <T> T fromJsonNode(String fieldName, String jsonString, Class<T> clazz) {
        JsonNode jn = getJsonNode(fieldName, jsonString);
        if (jn != null) {
            return fromJson(jn.get(fieldName).toString(), clazz);
        }
        return null;
    }

    /**
     * 从json中抽取子json,转Object
     *
     * @param fieldName
     * @param jsonString
     * @return
     */
    public static <T> T fromJsonNode(String fieldName, String jsonString, TypeReference<T> tr) {
        JsonNode jn = getJsonNode(fieldName, jsonString);
        if (jn != null) {
            return fromJson(jn.get(fieldName).toString(), tr);
        }
        return null;
    }

    /**
     * T 可以是List,Map
     *
     * @param <T>
     * @param jsonString
     * @param tr
     * @return
     */
    public static <T> T fromJson(String jsonString, TypeReference<T> tr) {
        try {
            return mapper.readValue(jsonString, tr);
        } catch (Exception e) {
            log.error(jsonString, e);
        }
        return null;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     *
     * @param object
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("toJson", e);
        }
        return null;
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            log.error(jsonString, e);
        }
        return null;
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.
     */
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error(jsonString, e);
        }
        return null;
    }

}
