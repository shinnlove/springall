# springall
spring技术选型集成，不同分支代表不同技术选型，集成了常用技术的spring分支，不再零散。

# services分支
spring远程服务集成：RMI、HttpInvoker、Hessian等。


# 一、spring集成

spring对常见的RPC框架提供了相应的集成支持，统一标准化了RPC服务发布与引用，简化了开发流程。主要包含如下远程调用：

- Remote Method Invocation(RMI)
- HTTP Invoker
- Hessian
- JAX-WS
- JMS

不论是哪一种RPC框架，他们对应的`XXXFactoryBean`都实现了接口`org.springframework.beans.factory.FactoryBean`在客户端上下文中引入服务代理，而使用`XXXServiceExporter`在服务端某端口号上发布远程服务。

# 二、RMI、HTTP Invoker、Hessian框架的集成

`RMI`和`HTTP Invoker`使用的都是java内置的序列化协议。

## 1、RMI与RmiProxyFactoryBean

**使用rmi传输不是基本类型的，都必须实现`java.io.Serializable`接口。**

spring的rmi集成所在的jar坐标：
```xml
    <properties>
        <spring.version>4.2.6.RELEASE</spring.version>
    </properties>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
    </dependency>
```

发布rmi服务：
### a) 定义一个服务接口和实现，并配置到spring上下文中
### b) 使用`RmiServiceExporter`发布服务
如：
```xml
    <bean id="userService" class="service.impl.UserServiceImpl"/>

    <!-- 发布RMI远程服务 -->
    <bean class="org.springframework.remoting.rmi.RmiServiceExporter">
        <property name="serviceName" value="userRmiService"/>
        <property name="service" ref="userService"/>
        <property name="serviceInterface" value="service.UserService"/>
        <!-- RMI 默认端口为1099，这里改个端口号 -->
        <property name="registryPort" value="1199"/>
    </bean>
```

引用rmi服务：
### a) 使用`RmiProxyFactoryBean`引用服务
```xml
    <!-- 引入RMI远程服务 -->
    <bean id="userRmiServiceProxy" class="org.springframework.remoting.rmi.RmiProxyFactoryBean">
        <!-- rmi协议地址 -->
        <property name="serviceUrl" value="rmi://127.0.0.1:1199/userRmiService"/>
        <!-- rmi协议服务接口 -->
        <property name="serviceInterface" value="service.UserService"/>
    </bean>
```

## 2、HTTP Invoker与HttpInvokerProxyFactoryBean

`HTTP Invoker`的支持是在`spring-web`、`spring-remoting`中的。它传输采用`HTTP`协议，遵循`servlet`规范。

发布`HTTP Invoker`服务：
### a) 使用spring的dispatcherServlet配置web.xml
将所有后缀为`.httpInvoker`的请求拦截下来。
```xml
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:/META-INF/spring/httpinvoker-server.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <!-- 拦截所有后缀为`.httpInvoker`的请求 -->
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>*.httpInvoker</url-pattern>
  </servlet-mapping>
```

### b) 使用`SimpleUrlHandlerMapping`配置url映射

使用spring的`org.springframework.web.servlet.handler.SimpleUrlHandlerMapping`来处理servlet拦截下来的`*.httpInvoker`请求。
```xml
    <!-- 呼应servlet的web.xml中servlet-mapping映射的请求 -->
    <!-- servlet-mapping会拦截所有的`.httpInvoker`请求，这里指定一个`user.httpInvoker`被`serviceHttpInvoker`处理 -->
    <bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <!-- 配置导出为HttpInvoker服务 -->
                <prop key="/user.httpInvoker">serviceHttpInvoker</prop>
            </props>
        </property>
    </bean>
```

### c) 使用`HTTPInvoker`服务导出类发布服务

```xml
    <!-- 使用HttpInvoker服务导出类发布服务`serviceHttpInvoker` -->
    <bean id="serviceHttpInvoker" class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">
        <property name="service" ref="userService"/>
        <property name="serviceInterface" value="com.shinnlove.services.service.UserService"/>
    </bean>
```

最后，客户端使用如下配置启动，就可以从bean工厂中取出`服务代理`对象了。

```xml
    <!-- 使用`HttpInvokerProxyFactoryBean`在客户端spring上下文中生成代理，服务地址是IP地址+端口号+服务路径，接口必须指定 -->
    <bean id="userServiceProxy" class="org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean">
        <property name="serviceUrl" value="http://127.0.0.1:8080/user.httpInvoker"/>
        <property name="serviceInterface" value="com.shinnlove.services.service.UserService"/>
    </bean>
```

**特别注意：因为`HTTP Invoker`是要运行在servlet上的，因此要使用tomcat进行部署。要选择启动目录是`/`而不是`/项目名`，否则请求的时候会无法匹配的`*.httpInvoker`。**


## 3、Hessian与HessianProxyFactoryBean

和`HTTPInvoker`类似，服务发布类替换成了`HessianServiceExporter`，服务引入类替换成了`HessianProxyFactoryBean`。多配置一个`servlet-mapping`和`simpleUrlMapping`即可。

注意引用`hessian`的jar：
```xml
    <!-- spring 集成 hessian需要这个jar -->
    <dependency>
        <groupId>com.caucho</groupId>
        <artifactId>hessian</artifactId>
        <version>4.0.7</version>
    </dependency>
```

**如果配置了servlet-mapping而spring中不见响应后缀的处理类(默认后缀的除外)，idea会标红报警。**
