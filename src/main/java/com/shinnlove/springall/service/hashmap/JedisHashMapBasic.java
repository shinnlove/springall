package com.shinnlove.springall.service.hashmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import com.alibaba.fastjson.JSON;
import com.shinnlove.springall.model.Student;

/**
 * jedis的HashMap基础使用。
 *
 * @author shinnlove.jinsheng
 * @version $Id: JedisStringBasic.java, v 0.1 2019-05-01 10:50 shinnlove.jinsheng Exp $$
 */
public class JedisHashMapBasic {

    /** jedis单连redis客户端 */
    private static Jedis        jedis = new Jedis("127.0.0.1", 6379);

    /** hash存取的field名 */
    private static final String ID    = "id";

    /** hash存取的field名 */
    private static final String NAME  = "name";

    /** hash存取的field名 */
    private static final String AGE   = "age";

    public static void main(String[] args) {
        simpleHSet();

        hGetLenExist();

        multipleHSet();

        multipleHGet();

        keysAndVals();

        hgetall();

        hscan();
    }

    /**
     * 一次像hashMap中放入一个值。
     */
    public static void simpleHSet() {
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
     * 
     * hlen返回某个key下的hash字段数量、hexist判断某个key下指定field对应的键是否存在。
     */
    public static void hGetLenExist() {
        // 准备要存redis的对象
        Student student = new Student(1L, "tony", 24);
        String key = "student:" + student.getId();

        String id = jedis.hget(key, ID);
        String name = jedis.hget(key, NAME);
        String age = jedis.hget(key, AGE);

        long fLen = jedis.hlen(key);

        System.out.println("从redis读取的student对象是：id=" + id + ", name=" + name + ", age=" + age
                           + ", 总计" + fLen + "个字段");

        boolean exist = jedis.hexists(key, NAME);
        System.out.println("name字段在student:" + student.getId() + "中存在结果exist=" + exist);
    }

    /**
     * 一次网络请求合并放入多个hash对应的值、类似字符串的mset命令。
     */
    public static void multipleHSet() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        Map<String, String> map = new HashMap<>();
        map.put(ID, "2");
        map.put(NAME, "evelyn");
        map.put(ID, "24");

        String result = jedis.hmset(key, map);
        System.out.println("一次批量设置的结果result=" + result);
    }

    /**
     * 一次得到多个指定的field对应的值，传入泛参、返回List值列表。
     */
    public static void multipleHGet() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        List<String> values = jedis.hmget(key, ID, NAME, AGE);
        for (String value : values) {
            System.out.println("current value=" + value);
        }
    }

    /**
     * hkeys返回某个key下所有的hash字段、hvals返回某个key下所有的hash字段对应的value。
     */
    public static void keysAndVals() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        // 返回所有的key是无序集合
        Set<String> fieldSet = jedis.hkeys(key);
        for (String field : fieldSet) {
            System.out.println("field=" + field);
        }

        // 返回hash值是有序的
        List<String> values = jedis.hvals(key);
        for (String value : values) {
            System.out.println("value=" + value);
        }
    }

    /**
     * hgetall命令，能获取指定key下的所有field/value。
     * 
     * 特别注意：如果field和value特别多，有可能阻塞redis的可能，建议使用hmget获取部分key、或使用hscan遍历所有key。
     * 
     */
    public static void hgetall() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        Map<String, String> fieldValues = jedis.hgetAll(key);
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            System.out.println("field[" + entry.getKey() + "]=value[" + entry.getValue() + "] ");
        }
    }

    /**
     * Redis2.8版本后渐进式遍历指定key下的field/value方式。
     */
    public static void hscan() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        // 遍历参数
        String cursor = "0";

        ScanParams scanParams = new ScanParams();
        scanParams.match("new:student*");

        while (true) {
            // 默认从cursor位置开始、一次遍历10个键值
            ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(key, cursor, scanParams);

            List<Map.Entry<String, String>> result = scanResult.getResult();

            System.out.println("一次hscan遍历结果集大小size=" + result.size());

            for (Map.Entry<String, String> entry : result) {
                System.out.println("field=[" + entry.getKey() + "], value=[" + entry.getValue()
                                   + "]");
            }

            cursor = scanResult.getStringCursor();
            if ("0".equals(cursor)) {
                break;
            }

        } // while

        System.out.println("所有键值都被遍历完成。");
    }

}
