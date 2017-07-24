package com.mySBoot.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;

import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

public class RedisUtil {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private static StringRedisTemplate springRedisTemplate;


    static {
    	springRedisTemplate = (StringRedisTemplate) BeanUtil.getAppContext().getBean("redisTemplate");
    }
    
    /**
     * 删除缓存记录
     *
     * @param key
     */
    public static void delete(String key) {
        try {
            springRedisTemplate.delete(key);
        } catch (Exception e) {
            handleJedisException(e);
        }
    }

    /**
     * 设置对象类型缓存项，无失效时间
     *
     * @param key
     * @param value
     */
    public static <V> boolean set(String key, V value) {
        return set(key, value, -1);
    }

    /**
     * 设置key-value项，字节类型
     *
     * @param key
     * @param value
     */
    public static <V> boolean set(String key, V value, int exp) {
        try {
            String serialize = serialize(value);
            if (exp > 0) {
                springRedisTemplate.opsForValue().set(key, serialize, exp, TimeUnit.SECONDS);
            } else {
                springRedisTemplate.opsForValue().set(key, serialize);
            }
        } catch (Exception e) {
            handleJedisException(e);
            return false;
        }
        return true;
    }

    /**
     * 获取对象类型
     *
     * @param key
     * @return
     */
    public static <V> V get(String key, Class<V> v) {
        String data = getString(key);
        if (data != null) {
            return deserialize(data, v);
        }
        return null;
    }

    /**
     * 获取字符串类型
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            String value = springRedisTemplate.opsForValue().get(key);
            return value;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 获取所有列表(默认从左边第一个开始)
     *
     * @param listKey
     * @return
     */
    public static <T> List<T> getListAll(String listKey, Class<T> clazz) {
        return getListByRange(listKey, clazz, 0, Integer.MAX_VALUE);
    }

    /**
     * 获取列表所有数据
     *
     * @param listKey
     * @return
     */
    public static <T> List<T> getListByRange(String listKey, Class<T> clazz, Integer offset, Integer limit) {
        List<T> result = Lists.newArrayList();
        List<String> list = getListRange(listKey, offset, limit);
        if (list != null) {
            for (String string : list) {
                result.add(deserialize(string, clazz));
            }
        }
        return result;
    }


    /**
     * 获取list某一范围的段
     *
     * @param listKey
     * @param start
     * @param end
     * @return
     */
    public static List<String> getListRange(String listKey, int start, int end) {

        try {
            return springRedisTemplate.opsForList().range(listKey, start, end);
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 获取Map结构所有数据(key为String)
     *
     * @param mapKey
     * @return
     */
    public static Map<String, String> getStringMapAll(String mapKey) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            Map<String, String> result = hashOpt.entries(mapKey);
            return result;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    public static <V> void putToMap(String mapKey, String field, V value) {
        putStringToMap(mapKey, field, serialize(value));
    }

    /**
     * 添加到Map结构(key为String)
     *
     * @param mapKey
     * @param field
     * @param value
     */
    public static void putStringToMap(String mapKey, String field, String value) {
        try {
            springRedisTemplate.opsForHash().put(mapKey, field, value);
        } catch (Exception e) {
            handleJedisException(e);
        }
    }

    public static void addStringToList(String key, String value) {
        try {
            springRedisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            handleJedisException(e);
        }
    }

    public static <V> void addToList(String mapKey, V value) {
        addStringToList(mapKey, serialize(value));
    }

    /**
     * 添加到Map结构（key为String）返回 批量设置成功的记录数,失败返回-1
     *
     * @param mapKey
     * @param data
     */
    public static int putStringToMap(String mapKey, Map<String, String> data) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            hashOpt.putAll(mapKey, data);
        } catch (Exception e) {
            handleJedisException(e);
            return -1;
        }
        return data.size();
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static String getStringFromMap(String mapKey, String field) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            String data = hashOpt.get(mapKey, field);
            return data;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 判断Map结构是否存在field
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static boolean hexistsString(String mapKey, String field) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            return hashOpt.hasKey(mapKey, field);
        } catch (Exception e) {
            handleJedisException(e);
        }
        return false;
    }

    /**
     * 判断Map结构是否存在field
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static <F> boolean hexists(String mapKey, F field) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            return hashOpt.hasKey(mapKey, serialize(field));
        } catch (Exception e) {
            handleJedisException(e);
        }
        return false;
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static <F, V> V getFromMap(String mapKey, String field, Class<V> clazz) {
        return deserialize(getStringFromMap(mapKey, field), clazz);
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static <F> String getFromMap(String mapKey, F field) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            String data = hashOpt.get(mapKey, serialize(field));
            return data;
        } catch (Exception e) {
            handleJedisException(e);
        }
        return null;
    }

    /**
     * 从map中移除记录
     *
     * @param mapKey
     * @param field
     */
    public static <F> void removeFromMap(String mapKey, F field) {
        removeStringFromMap(mapKey, serialize(field));
    }

    /**
     * 从map中移除某个filed的记录
     *
     * @param mapKey
     * @param field
     * @return
     */
    public static long removeStringFromMap(String mapKey, String field) {
        try {
            HashOperations<String, String, String> hashOpt = springRedisTemplate.opsForHash();
            return hashOpt.delete(mapKey, field);
        } catch (Exception e) {
            handleJedisException(e);
        }
        return -1;
    }

    /**
     * map数据序列化转换
     *
     * @param data
     * @return
     */
    public static Map<String, String> serializeMap(Map<Object, Object> data) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            Set<Object> keys = data.keySet();
            if (keys != null && keys.size() > 0) {
                for (Object key : keys) {
                    result.put(serialize(key), serialize(data.get(key)));
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String v, Class<T> type) {
        if (StringUtils.isBlank(v)) {
            return null;
        }
        return (T) JsonUtil.fromJson(v, TypeFactory.rawClass(type));
    }

    public static String serialize(Object t) {
        if (t == null) {
            return new String();
        }
        return JsonUtil.toJson(t);
    }

    private static boolean handleJedisException(Exception jedisException) {
        jedisException.printStackTrace();
        if (jedisException instanceof JedisConnectionException) {
            LOGGER.error("redis connection lost.", jedisException);
        } else if (jedisException instanceof JedisDataException) {
            if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
                LOGGER.error("redis are read-only slave.", jedisException);
            } else {
                return false;
            }
        } else {
            LOGGER.error("Jedis exception happen.", jedisException);
        }
        return true;
    }

    /**
     * 向队尾增加一个元素
     *
     * @param key
     * @param value
     * @return
     * @author chenzhangwei
     * @time 2017年4月7日下午1:40:06
     */
    public static <V> boolean rightPush(String key, V value) {
        try {
            String serialize;
            if (value instanceof String) {
                serialize = (String) value;
            } else {
                serialize = serialize(value);
            }
            springRedisTemplate.opsForList().rightPush(key, serialize);
        } catch (Exception e) {
            handleJedisException(e);
            return false;
        }
        return true;
    }

    /**
     * 取队首元素
     *
     * @param key
     * @return
     * @author chenzhangwei
     * @time 2017年4月7日下午1:40:06
     */
    public static String leftPop(String key) {
        String data = springRedisTemplate.opsForList().leftPop(key);
        return data;
    }

    public static String leftPop(String key, long time, TimeUnit timeUnit) {
        String data = springRedisTemplate.opsForList().leftPop(key, time, timeUnit);
        return data;
    }

}
