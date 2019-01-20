# springall
spring技术选型集成，不同分支代表不同技术选型，集成了常用技术的spring分支，不再零散。

# spring集成logback日志框架

spring集成logback和普通的集成log4j/log4j2差不多。

## 一、jar版本

日志门面选择通用的即可。`logback-classic`中包含`logback-api`和`logback-core`两个jar。

`janino`是在`logback.xml`使用if表达式时需要引入的解析jar，如果不用表达式可以不引。

`lombok`是注解驱动的`logback`日志方式，如`@Slf4j`这样的注解打了就代表打日志。

最后`logback-ext-spring`这个jar是用来在`web.xml`中定义`servlet`的监听器，将日志加载到上下文中的。建议版本选择`0.1.2`以上的。

```xml
<!-- slf4j门面 -->
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <version>${slf4j.version}</version>
</dependency>
<!-- classic and core -->
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.0.13</version>
</dependency>
<!-- expression languages -->
<dependency>
  <groupId>org.codehaus.janino</groupId>
  <artifactId>janino</artifactId>
  <version>2.6.1</version>
</dependency>
<!-- lombok annotation -->
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.12.4</version>
</dependency>
<!-- servlet context listener -->
<dependency>
  <groupId>org.logback-extensions</groupId>
  <artifactId>logback-ext-spring</artifactId>
  <version>0.1.3</version>
</dependency>
```



## 二、logback.xml

logback日志文件的定义和log4j差不多，通常都是CONSOLE、DEBUG、INFO、WARN、ERROR等级别的日志。

如果jar中输出日志噪音比较大，可以适当提高jar中的日志级别。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="true">

    <!-- 模块名称， 影响日志配置名，日志文件名 -->
    <property name="appName" value="springall"/>
    <property name="logMaxSize" valule="100MB"/>
    <!--rootPath 日志路径，这里是相对路径，web项目eclipse下会输出到eclipse的安装目录下，如果部署到linux上的tomcat下，会输出到tomcat/bin目录 下 -->
    <property name="rootPath" value="/Users/zhaochensheng/Downloads"/>
    <contextName>${appName}</contextName>

    <!--控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss}|%t|%p| %m |%logger:%L%n</pattern>
            <!-- 控制台也要使用UTF-8，不要使用GBK，否则会中文乱码 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- debug日志输出 -->
    <appender name="DEBUG" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 设置日志不超过${logMaxSize}时的保存路径，注意如果 是web项目会保存到Tomcat的bin目录 下 -->
        <file>${rootPath}/${appName}/logs/${appName}-debug.log</file>
        <!-- 滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件。-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${rootPath}/${appName}/logs/${appName}-debug-%d{yyyy-MM-dd}-%d.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <!-- 当天的日志大小 超过${logMaxSize}时,压缩日志并保存 -->
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <!-- 日志输出的文件的格式  -->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss}|%t|%p| %m |%logger:%L%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>DEBUG</level>
        </filter>
    </appender>

    <!-- 信息日志输出 -->
    <appender name="INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${rootPath}/${appName}/logs/${appName}-info.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${rootPath}/${appName}/logs/all/${appName}-info-%d{yyyy-MM-dd}-%i.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss}|%t|%p| %m |%logger:%L%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 告警日志输出 -->
    <appender name="WARN" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${rootPath}/${appName}/logs/${appName}-warn.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${rootPath}/${appName}/logs/all/${appName}-warn-%d{yyyy-MM-dd}-%i.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss}|%t|%p| %m |%logger:%L%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>WARN</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 错误日志输出 -->
    <appender name="ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${rootPath}/${appName}/error/${appName}-error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${rootPath}/${appName}/logs/all/${appName}-error-%d{yyyy-MM-dd}-%e.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss}|%t|%p| %m |%logger:%L%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 为某个包下的所有类的指定Appender 这里也可以指定类名称例如：com.aa.bb.ClassName,下面还有另一种写法，就是涉及到name="关键包的日志输出级别" -->
    <!-- 减少噪音日志的输出 -->
    <!--     <logger name="com.lin" additivity="false">   -->
    <!--         <level value="debug" />   -->
    <!--         <appender-ref ref="stdout" />   -->
    <!--         <appender-ref ref="file" />   -->
    <!--     </logger>   -->
    <logger name="jdbc" level="INFO"/>
    <logger name="org" level="INFO"/>
    <logger name="net" level="INFO"/>
    <logger name="sql" level="INFO"/>
    <logger name="java.sql" level="INFO"/>
    <logger name="javax" level="INFO"/>

    <!--日志的输出级别由低到高（越来问题越严重）trace->debug->info->warn->error -->
    <!-- root将级别为DEBUG及大于DEBUG的日志信息交给已经配置好的name='STDOUT'的appender处理，将信息打印到控制台-Console -->
    <root level="DEBUG">
        <!-- appender-ref标识这个appender将会添加到本应用的日志系统中 -->
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="DEBUG"/>
        <appender-ref ref="INFO"/>
        <appender-ref ref="WARN"/>
        <appender-ref ref="ERROR"/>
    </root>

</configuration>
```



## 三、web.xml监听

在项目中引用logback后，需要让spring起来的时候servlet容器监听到logback相关配置的变化等。

```xml
<!-- logback日志配置 -->
<!-- 特别注意配置文件最好在classpath下，不要自己建目录!!! -->
<context-param>
  <description>logback日志配置文件路径</description>
  <param-name>logbackConfigLocation</param-name>
  <param-value>classpath:logback.xml</param-value>
</context-param>
<listener>
  <listener-class>ch.qos.logback.ext.spring.web.LogbackConfigListener</listener-class>
</listener>
```

这样就有了logback自己的listener。



## 四、logback打印

最后使用SLF4j的API，将日志打印出来，java代码如下：

```java
package com.shinnlove.springall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shinnlove.springall.util.log.LoggerUtil;

@RestController
@RequestMapping(value = "/hello")
public class HelloSpringController {

    /** logback日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloSpringController.class);

    @RequestMapping(value = "/logback", method = { RequestMethod.GET, RequestMethod.POST })
    public String sayHello() {
        LoggerUtil.info(LOGGER, "你好，我是logback的同步日志输出。");
        LoggerUtil.warn(LOGGER, "你好，我的英文名叫shinnlove。");
        LoggerUtil.error(LOGGER, new RuntimeException("这是我自定义的错误"));
        return "This is logback test.";
    }

}
```

在自己的下载目录下就能看到应用打出来的日志了，即便没有相关目录应用也会自动创建出目录和日志文件的。










