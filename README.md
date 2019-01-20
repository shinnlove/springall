# springall
spring技术选型集成，不同分支代表不同技术选型，集成了常用技术的spring分支，不再零散。

# spring-log4j2：spring工程集成log4j2、logback等高性能日志样板代码


# Spring工程配置log4j2

一代的`Log4j`从性能上已经被现在诸多优秀的日志框架超越（如`logback`），二代`log4j2`日志孕育而生。

经测试，log4j2在性能上有显著提高，有如下特点：

- 与logback一样可以动态加载修改过的配置，加载过程中也不会丢失日志（热部署）
- 实现了API和实现模块分离（`log4j-api.jar`、`log4j-core.jar`）
- `log4j2`异步记录日志功能在一个单独的线程里执行I/O操作来提高性能
- 独有的异步`Appender`和异步`Logger`，使用无锁的`Disruptor RingBuffer`来实现
- 记录日志性能随着线程数的增加吞吐量也在增加，在多核CPU系统中能达到更好的性能

## 一、spring集成log4j2

在`spring`中集成`log4j2`需要若干步骤的配置，详见下方1~6点。

**建议fork后拉下来看一下代码结构，对着doc配置会更有体感。**

### 1、相关jar

`log4j2`的相关jar针对第一个log版本有所改变，更深刻的了解什么时候需要什么jar，才能细致的排查任何潜在的问题。

这里将分层次讲解`log4j2`集成所需的jar。

#### a) log4j2自身

```xml
<!-- Log4j2 的依赖 -->
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-api</artifactId>
  <version>${log4j2.version}</version>
</dependency>
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-core</artifactId>
  <version>${log4j2.version}</version>
</dependency>
```

#### b) log4j2异步无锁算法支持

异步`appender`、`logger`需要`disruptor ringbuffer`支持。

```xml
<!-- log 异步无锁支持jar -->
<dependency>
  <groupId>com.lmax</groupId>
  <artifactId>disruptor</artifactId>
  <version>${async.disruptor.version}</version>
</dependency>
```

#### c) slf4j日志门面

```xml
<!-- slf4j门面 -->
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <version>${slf4j.version}</version>
</dependency>
```

#### d) log4j2对web应用的支持

`log4j2`对web应用的支持：

```xml
<!-- 在代码中使用Spring管理log4j2,通过Spring进行初始化，并应用Druid的数据源 -->
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-web</artifactId>
  <version>${log4j2.version}</version>
</dependency>
```

#### e) log4j2延伸支持

以下内容是可选的：

```xml
<!-- log 支持 nosql -->
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-nosql</artifactId>
  <version>${log4j2.version}</version>
</dependency>

<!-- jcl不知道干嘛的 -->
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-jcl</artifactId>
  <version>${log4j2.version}</version>
</dependency>
```

#### f) log4j2桥接slf4j

为了防止日志的多重绑定，以下`log4j2`与`slf4j`衔接的jar选一个即可，我选择了`log4j-slf4j-impl`这种类型的桥接。

```xml
<!-- log4j2桥接slf4j，便于使用slf4j声明，log4j2输出日志 -->
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-slf4j-impl</artifactId>
  <version>${log4j2.version}</version>
</dependency>
<!-- 与`log4j-slf4j-impl`一样让sfl4j对接log4j2，只要引用一个即可 -->
<!--<dependency>-->
<!--<groupId>org.slf4j</groupId>-->
<!--<artifactId>slf4j-log4j12</artifactId>-->
<!--<version>${slf4j.version}</version>-->
<!--</dependency>-->
```

#### g) 若干jar版本的建议

上述若干jar的版本，我选用了一些稳定版本，可以参见`maven pom`中的`properties`，如下：

```xml
<properties>
  <spring.version>4.2.6.RELEASE</spring.version>
  <jackson.version>2.7.4</jackson.version>
  <slf4j.version>1.7.21</slf4j.version>
  <log4j2.version>2.8.2</log4j2.version>
  <async.disruptor.version>3.2.1</async.disruptor.version>
</properties>
```

### 2、log4j2配置文件及位置

`log4j2`的配置文件不能再用`xxx.properties`来命名了，只能是`xxx.xml`或`xxx.json`方式。

并且`log4j2`配置文件的位置建议直接放在`classpath`下，不要放的目录太深，经测试`servlet`找不到。

下面是项目中的log4j2配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status，这个用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，你会看到log4j2内部各种详细输出-->
<!--monitorInterval：Log4j能够自动检测修改配置 文件和重新配置本身，设置间隔秒数，线上可以实时调整这个文件-->
<configuration status="INFO" monitorInterval="30">
    <!--先定义所有的appender-->
    <appenders>
        <!--这个输出控制台的配置-->
        <console name="Console" target="SYSTEM_OUT">
            <!--输出日志的格式-->
            <PatternLayout pattern="%d{yyyy.MM.dd HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </console>
        <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，这个也挺有用的，适合临时测试用-->
        <File name="test" fileName="logs/test.log" append="false">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </File>
        <!-- 这个会打印出所有的info及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileInfo" fileName="${sys:user.home}/Downloads/logs/info.log"
                     filePattern="${sys:user.home}/Downloads/logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout
                    pattern="%d [%X{traceId} %X{rpcId} - %X{loginUserEmail}/%X{loginUserID}/%X{remoteAddr}/%X{clientId} - %X{requestURIWithQueryString}] %-5p %c{2} - %m%n"
                    charset="UTF-8"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
        <RollingFile name="RollingFileWarn" fileName="${sys:user.home}/Downloads/logs/warn.log"
                     filePattern="${sys:user.home}/Downloads/logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout
                    pattern="%d [%X{traceId} %X{rpcId} - %X{loginUserEmail}/%X{loginUserID}/%X{remoteAddr}/%X{clientId} - %X{requestURIWithQueryString}] %-5p %c{2} - %m%n"
                    charset="UTF-8"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <!-- DefaultRolloverStrategy属性如不设置，则默认为最多同一文件夹下7个文件，这里设置了20 -->
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <RollingFile name="RollingFileError" fileName="${sys:user.home}/Downloads/logs/error.log"
                     filePattern="${sys:user.home}/Downloads/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            <!-- 单机模式下格式 -->
            <!--<PatternLayout pattern="%d{yyyy.MM.dd HH:mm:ss.SSS} [%-5level] %class{36}.%M:%L - %msg%xEx%n"/>-->
            <!-- 分布式下日志格式 -->
            <PatternLayout
                    pattern="%d [%X{traceId} %X{rpcId} - %X{loginUserEmail}/%X{loginUserID}/%X{remoteAddr}/%X{clientId} - %X{requestURIWithQueryString}] %-5p %c{2} - %m%n"
                    charset="UTF-8"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <!--过滤掉spring和mybatis的一些无用的DEBUG信息-->
        <logger name="org.springframework" level="INFO"></logger>
        <logger name="org.mybatis" level="INFO"></logger>
        <root level="all">
            <appender-ref ref="Console"/>
            <appender-ref ref="test"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
        </root>
    </loggers>
</configuration>
```

可以根据项目层级需要细分`appenders`中的`RollingFile`并把它挂到`loggers`中。

### 3、servlet中集成

`log4j2`在正常的java程序中怎么使用就不叙述了，这里主要讲解在spring（servlet类应用）中如何集成。

**之前配置日志，各种appender只要稍微配置错一点，整个日志文件可能就出不来，经过N次调试，终于解决了所有的问题。**

在`Spring`中配置`log4j2`是由`servlet`单独日志上下文来扫描日志配置和刷新配置的。

#### a) 一代的`log4j`配置：

```xml
<context-param>
  <param-name>log4jConfigLocation</param-name>
  <param-value>classpath:META-INF/log/log4j.xml</param-value>
</context-param>
<context-param>
  <param-name>log4jRefreshInterval</param-name>
  <param-value>60000</param-value>
</context-param>
<listener>
  <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
</listener>
```

其中`Log4jConfigListener`已经过时了。并且`log4j`的性能完全不如`logback`等优秀的日志框架。

#### b) 二代的`log4j2`配置：

```xml
<!-- log4j2日志配置 -->
<!-- 特别注意配置文件最好在classpath下，不要自己建目录!!! -->
<context-param>
  <description>log4j2日志配置文件路径</description>
  <param-name>log4jConfigLocation</param-name>
  <param-value>classpath:log4j2.xml</param-value>
</context-param>
<listener>
  <listener-class>org.apache.logging.log4j.web.Log4jServletContextListener</listener-class>
</listener>
```

**注意将`log4j2`的配置文件放到`classpath`类路径下，我试过放到`classpath:META-INF/logs/log4j2.xml`没什么用。**

### 4、slf4j日志门面衔接log4j2

特别注意导入的`Logger`和`LoggerFactory`是`slf4j`的日志门面，`getLogger`也可以直接指定用哪个`appender`。这里使用`Class类字面变量`，这个class在哪个层级就调用哪个层级的`logger appender`。

```java
package com.shinnlove.logs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shinnlove.logs.util.log.LoggerUtil;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {
  
    /** log4j2日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
  
    @RequestMapping(value = "/log4j2", method = { RequestMethod.GET, RequestMethod.POST })
    public String sayHello() {
        LoggerUtil.info(LOGGER, "你好，我是log4j2的同步日志输出。");
        LoggerUtil.warn(LOGGER, "你好，我的英文名叫shinnlove。");
        LoggerUtil.error(LOGGER, new RuntimeException("这是我自定义的错误"));
        return "This is log4j2 test.";
    }
}
```

### 5、日志的打印格式

#### a) 日志打印规范

打印日志的规范也有讲究，为了日后更好的做日志采集和监控，配合`Chukwa`等采集工具截取日志，我们的日志必须有统一的格式、良好的可读性。

必须遵循以下几点：

- 毫秒必须带上，不要打印诸如：XXX..... CST类型的日期
- 分布式环境下应用打日志，必须带上`spanId`、`rpcId`等分布式`trace`日志的标记
- 头行打印日志的标题、出错的类和方法、行号
- 换行紧跟着完整的日志堆栈

以下是一个良好的日志格式demo：

```java
2018-08-12 13:59:25,237 [  - /// - ] ERROR controller.HelloController - [32]
java.lang.RuntimeException: 这是我自定义的错误
    at com.shinnlove.logs.controller.HelloController.sayHello(HelloController.java:32) [classes/:?]
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_112]
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_112]
```

#### b) 日志公共类封装

以下是日志公共的日志打印工具，一般放在应用的util层。

```java
/**
 * 规范化日志打印工具，注意日志的级别选择：<br>
 *
 * <p>
 *   <ol>
 *     <li>DEBUG <b>开发环境</b>应用调试，输出详细的应用状态
 *     <li>INFO <b>生产环境</b>运行状态观察，输出应用生命周期的中<b>正常重要事件</b>
 *     <li>WARN <b>生产环境</b>故障诊断，输出应用中的<b>可预期的异常事件</b>
 *     <li>ERROR <b>生产环境</b>境故障诊断，输出应用中的<b>未预期的异常事件</b>
 *   </ol>
 * </p>
 *
 * @author shinnlove.jinsheng
 * @version $Id: LoggerUtil.java, v 0.1 2016-07-31 下午11:42 shinnlove.jinsheng Exp $$
 */
public final class LoggerUtil {

    /** 线程编号修饰符 */
    private final static char THREAD_RIGHT_TAG = ']';

    /** 线程编号修饰符 */
    private final static char THREAD_LEFT_TAG  = '[';

    /** 换行符 */
    public final static char  ENTERSTR         = '\n';

    /** 逗号 */
    public final static char  COMMA            = ',';

    /**
     * 禁用构造函数
     */
    private LoggerUtil() {
        // 禁用构造函数
    }

    /**
     * 生成<font color="blue">调试</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void debug(Logger logger, Object... obj) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(obj));
        }
    }

    /**
     * 生成<font color="blue">通知</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void info(Logger logger, Object... obj) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(obj));
        }
    }

    /**
     * 生成<font color="brown">警告</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * <B><font color="brown">注意！这个方法不能打印异常，打印异常必须用下面的方法</font>
     * @param logger
     * @param obj
     */
    public static void warn(Logger logger, Object... obj) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogString(obj));
        }
    }

    /**
     * 生成<font color="brown">警告</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param t
     * @param obj
     */
    public static void warn(Logger logger, Throwable t, Object... obj) {
        if (logger.isWarnEnabled()) {
            List list = new ArrayList();
            for (Object o : obj) {
                list.add(o);
            }

            list.add(t);
            logger.warn(getLogString(list));
        }
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param obj 任意个要输出到日志的参数
     * @return
     */
    public static String getLogString(Object... obj) {
        StringBuilder log = new StringBuilder();
        log.append(THREAD_LEFT_TAG).append(Thread.currentThread().getId()).append(THREAD_RIGHT_TAG);
        for (Object o : obj) {
            log.append(o);
        }
        return log.toString();
    }

    /**
     * 生成<font color="brown">错误</font>级别日志<br>
     *
     * @param logger   日志
     * @param e        异常
     * @param obj      日志参数
     */
    public static void error(final Logger logger, final Throwable e, final Object... obj) {
        logger.error(getLogString(obj), e);
    }

}
```

打印时候参见4点中`Controller`中的日志打印方式。

### 6、异步配置

配置JVM启动参数：

`- DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`，然后把`Disruptor`的jar包放入类路径中即可。

## 二、log4j2的一些参考文献

以下内容可以作为参考，每个人配置的细节有异同。

- [Log4J2入门·配置详解](https://blog.csdn.net/womeng2009/article/details/53510913/)
- [聊一聊log4j2配置文件log4j2.xml](https://www.cnblogs.com/hafiz/p/6170702.html)
- [log4j2.xml和log4j.properties的指定配置路径方法](https://blog.csdn.net/mathlpz126/article/details/79130977)；


- [解决 Log4jConfigListener 过期问题](https://blog.csdn.net/heatdeath/article/details/79355999)；
- [log4j2 日志配置实战](https://blog.csdn.net/u011277123/article/details/70899290)





