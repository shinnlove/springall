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
 * Redis对hash的操作在Jedis中对应的API。
 * 
 * 命令格式：hset key [field value...], hdel key [field value...].
 * hkeys, hvals, hlen, hincrBy...
 * 
 * 合理使用hash数据结构可以使数据内聚性更强、减少字符串存取领域模型key较多、序列化的问题。
 * 特别注意：保证hash中的数据精简、存储结构在ziplist中，不要蜕变成hashtable占用内存。
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

        hincr();

        keysAndVals();

        hgetall();

        hscan();

        hdel();
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
     * 
     * hmset和hset一样返回OK字符串代表成功。
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
     * 递增hash中某键值下field对应的值，hincrBy返回增加后的结果。
     */
    public static void hincr() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        multipleHSet();

        long result = jedis.hincrBy(key, AGE, 25);
        System.out.println("年龄增加到25岁后返回增加后的结果result=" + result);
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
     * 为hscan命令准备大量的field/value键值对。
     */
    private static void prepareHugeFieldValueForHScan(String key) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put("hello" + i, "tony" + i);
        }
        String result = jedis.hmset(key, map);
        System.out.println("设置大量field/value完成，result=" + result);
    }

    /**
     * Redis2.8版本后渐进式遍历指定key下的field/value方式。
     * 
     * 特别注意：hscan命令不是顺序读取的，读取下标位置随机，本来hash中的field/value就没有顺序可言。
     */
    public static void hscan() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        // 准备大量的field/value
        prepareHugeFieldValueForHScan(key);

        // 遍历参数
        String cursor = "0";

        ScanParams scanParams = new ScanParams();
        scanParams.match("he*");

        int count = 0;
        while (true) {
            // 默认从cursor位置开始、一次默认遍历10个键值，有时候也不一定完全10个值，有发现有12个值的
            ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(key, cursor, scanParams);

            List<Map.Entry<String, String>> result = scanResult.getResult();

            System.out.println("一次hscan遍历结果集大小size=" + result.size());

            // 循环读取这一次遍历的数据
            for (Map.Entry<String, String> entry : result) {
                System.out.println("field=[" + entry.getKey() + "], value=[" + entry.getValue()
                                   + "]");
            }

            // 判断游标决定后续有无要处理的数据
            cursor = scanResult.getStringCursor();
            if ("0".equals(cursor)) {
                break;
            }

            count++;
        } // while

        System.out.println("所有键值都被遍历完成，总计遍历了count=" + count + "次。");
    }

    /**
     * hdel命令作用域某个key下的若干个field，返回删除成功的field数量。
     */
    public static void hdel() {
        Student student = new Student(2L, "evelyn", 24);
        String key = "student:" + student.getId();

        Map<String, String> temp = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            temp.put("hello" + i, "tony" + i);
        }

        String setResult = jedis.hmset(key, temp);
        System.out.println("hash批量设置5个key=" + setResult);

        long delResult = jedis.hdel(key, "hello1", "hello2", "hello3");
        System.out.println("hash删除结果result=" + delResult);
    }

}
