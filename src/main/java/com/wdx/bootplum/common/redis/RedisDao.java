package com.wdx.bootplum.common.redis;

import com.wdx.bootplum.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * RedisDao 通过StringRedisTemplate类对redis进行操作
 * </p>
 *
 * @author gqc
 * @since 2019-04-17
 */
@Repository
public class RedisDao {

    @Autowired
    private StringRedisTemplate template;

    /**
     * 操作redis中数据结构为String的数据，进行set操作
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void setStringKey(String key, T value) {
        ValueOperations<String, String> ops = template.opsForValue();
        // 将参数value转换为String类型
        String str = JsonUtil.beanToString(value);
        ops.set(key, str);
    }

    /**
     * @Description: 操作redis中数据结构为List的数据，进行push操作(这里默认从左left进行插入)
     * @Param: [key, value]
     * @Retrun: void
     */
    public <T> long leftPushKey(String key, T value) {
        ListOperations<String, String> ops = template.opsForList();
        // 将参数value转换为String类型
        String str = JsonUtil.beanToString(value);
        // 将转换后的json字符串存入redis
        return ops.leftPush(key, str);
    }

    /**
     * @Description: 操作redis中数据结构为String的数据，进行increment操作
     * @Param: [key, num]
     * @Retrun: java.lang.Long
     */
    public Long incrOrDecr(String key, long num) {
        ValueOperations<String, String> ops = template.opsForValue();
        return ops.increment(key, num);
    }

    /**
     * @Description: 操作redis中数据结构为String的数据，进行get操作,获取单个对象的json字符串
     * @Param: [key, clazz]
     * @Retrun: T
     */
    public <T> T getStringValue(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String str = ops.get(key);
        // 将json串转换成对应(clazz)的对象
        return JsonUtil.stringToBean(str, clazz);
    }

    /**
     * @Description: 操作redis中数据结构为String的数据，进行get操作,获取字符串
     * @Param: [key, clazz]
     * @Retrun: T
     */
    public String getStringValue(String key) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.get(key);
    }
    /**
     * @Description: 操作redis中数据结构为String的数据，进行get操作,获取对象集合的json字符串
     * @Param: [key, clazz]
     * @Retrun: java.util.List<T>
     */
    public <T> List<T> getStringListValue(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String str = ops.get(key);
        // 将json串转换成对应(clazz)的对象集合
        return JsonUtil.stringToList(str, clazz);
    }

    /**
     * @Description: 操作redis中数据结构为List的数据，进行get操作,获取对应list中“所有”的数据
     * @Param: [key, clazz]
     * @Retrun: java.util.List<T>
     */
    public <T> List<T> getListValue(String key, Class<T> clazz) {
        ListOperations<String, String> ops = template.opsForList();
        // 获取对应list中的所有的数据
        List<String> list = ops.range(key, 0, -1);
        // 创建大小为对应list大小(ops.size(key)的ArrayList，避免后期进行扩容操作
//        List<T> result = new ArrayList<T>(ops.size(key).intValue());
//        // 遍历从redis中获取到的list，依次将其转换为对应(clazz)的对象并添加至结果集(result)中
//        for (String s : list) {
//            result.add(JsonUtil.stringToBean(s, clazz));
//        }
        return (List<T>) list;
    }

    /**
     * 模糊查询
     * @param pattern
     * @return
     */
    public Set keys(String pattern) {
        return this.template.keys("*" + pattern + "*");
        // return stringRedisTemplate.keys("?" + pattern);
        // return stringRedisTemplate.keys("[" + pattern + "]");
    }


    /**
     * 消息发布
     *
     * @param channelName 频道名称
     * @param value
     * @param <T>
     */
    public <T> void publish(String channelName, T value) {
        // 将参数value转换为String类型
        String str = JsonUtil.beanToString(value);
        // 将消息(str)发布到指定的频道(channelName)
        template.convertAndSend(channelName, str);
    }

    /**
     * 清空参数keyList中的所有值(key)所对应的redis里的数据
     *
     * @param keyList
     */
    public void cleanCache(List<String> keyList) {
        template.delete(keyList);
    }


}