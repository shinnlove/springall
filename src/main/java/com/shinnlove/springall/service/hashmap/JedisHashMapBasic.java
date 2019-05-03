package com.shinnlove.springall.service.hashmap;

import com.alibaba.fastjson.JSON;
import com.shinnlove.springall.model.Student;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * jedis的HashMap基础使用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JedisStringBasic.java, v 0.1 2019-05-01 10:50 shinnlove.jinsheng Exp $$
 */
public class JedisHashMapBasic {

    /** jedis单连redis客户端 */
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    /** hash存取的field名 */
    private static final String ID = "id";

    /** hash存取的field名 */
    private static final String NAME = "name";

    /** hash存取的field名 */
    private static final String AGE = "age";

    public static void main(String[] args) {
        simpleHSet();

        hGet();

        multipleHSet();
    }

    /**
     * 一次像hashMap中放入一个值。
     */
    public static void simpleHSet(){
        // 准备要存redis的对象
        Student student = new Student(1L, "tony", 24);
        String key = "student:" + student.getId();
        String value = JSON.toJSONString(student);

        jedis.hset(key, ID, String.valueOf(student.getId()));
        jedis.hset(key, NAME, student.getName());
        jedis.hset(key, AGE, String.valueOf(student.getAge()));
    }

    /**
     * 使用hashMap数据结构必须注意除了hash的key外，还要准备固定的常量字符字段，否则无法读取具体字段的值。
     */
    public static void hGet(){
        // 准备要存redis的对象
        Student student = new Student(1L, "tony", 24);
        String key = "student:" + student.getId();

        String id = jedis.hget(key, ID);
        String name = jedis.hget(key, NAME);
        String age = jedis.hget(key, AGE);

        long fLen = jedis.hlen(key);

        System.out.println("从redis读取的student对象是：id=" + id + ", name=" + name + ", age=" + age + ", 总计" + fLen + "个字段");
    }

    public static void multipleHSet(){
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        Map<String, String> map = new HashMap<>();
        map.put(ID, "2");
        map.put(NAME, "evelyn");
        map.put(ID, "24");

        String result = jedis.hmset(key, map);
    }

    public static void multipleHGet(){
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        Set<String> fieldSet = jedis.hkeys(key);
        for(String field : fieldSet){
            jedis.hmget(key, field);
        }

        List<String> values = jedis.hvals(key);

    }

}
