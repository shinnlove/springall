# springall
spring技术选型集成，不同分支代表不同技术选型，集成了常用技术的spring分支，不再零散。


# crawler分支是java爬虫



HttpClient基础、用法与爬虫，持续更新中......

# 一、HttpClient基础

## 1、类图结构

![image-20180703161602658](/var/folders/29/njnlrfm14tx1jn3nt8nqr46w0000gn/T/abnerworks.Typora/image-20180703161602658.png)

## 2、基本概念

要使用`HttpClient`进行请求需要进行如下步骤：

- 构造HttpClient客户端，可以自定义也可以使用默认
- 构造`HttpUriRequest`类型的请求对象，通常为Get或Post，将请求参数一并构造进去
- 客户端执行请求，返回`HttpResponse`类结果
- 取出`HttpResponse`的`entity`值，这个对象的content是一个`inputStream`流
- 通常使用`InputStreamReader`和`BufferedReader`构造出流的读取器
- 循环读取直到出现null行为止，把信息append到`StringBuilder`或者`StringBuffer`上
- 将结果转成字符串输出

其中，构造请求对象时：`HttpUriRequest`接口继承自`HttpRequest`接口，有两个常用基础导出类`HttpGet`和`HttpPost`（其他类型请求类推）。

## 2、实际操作

以下是某系统中一个服务端发送http请求的简单例子、参数设置等用的是默认的。

### a)、HttpClientUtils公共HTTP请求类

这个公共类里边有一个static的静态方法：这个方法向一个url发起请求，参数是paramMap，http加密方式是encode，一般为utf-8。

```java
/**
 * post 带参数提交
 *
 * @param url
 * @param paramMap
 * @param encode
 * @return
 */
public static String sendPost(String url, Map<String, String> paramMap, String encode) {
    HttpUriRequest request = getHttpClient(SENDMETHOD_POST, url, paramMap, encode);
    return execute(request, encode);
}
```

### b)、生成`HttpUriRequest`请求

在getHttpClient中，会根据请求的类型（当然`sendPost`肯定是post请求，`sendGet`就是get请求），去做不同的参数封装。一般get请求使用字符串拼接的方式得到一个url，而post方式使用`NameValuePair`来做键值对，而后使用`UrlEncodedFormEntity`来encode需要post的消息体，从而返回不同的HttpUriRequest。encode表单所使用的字符串一般为`utf-8`字符的，外部传入。

```java
/**
 * 获取httpclient
 *
 * @param method
 * @param url
 * @param paramMap
 * @param encode
 * @return
 */
private static HttpUriRequest getHttpClient(String method, String url,
                                            Map<String, String> paramMap, String encode) {
    HttpUriRequest request = null;
    if (StringUtil.equals(method, SENDMETHOD_GET)) {
        HttpGet httpGet = null;
        if (paramMap != null && paramMap.size() > 0) {
            // 使用URI这个类是否可以避免手动拼接参数？
            httpGet = new HttpGet(getUrlByParamMap(url, paramMap));
        } else {
            httpGet = new HttpGet(url);
        }
        request = httpGet;
    } else if (StringUtil.equals(method, SENDMETHOD_POST)) {
        HttpPost httpPost = new HttpPost(url);
        if (paramMap != null && paramMap.size() > 0) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            HttpEntity entity = null;
            for (String key : paramMap.keySet()) {
                formParams.add(new BasicNameValuePair(key, paramMap.get(key)));
            }
            try {
                entity = new UrlEncodedFormEntity(formParams, encode);
            } catch (UnsupportedEncodingException e) {
                LoggerUtil.error(logger, e, "字符转换异常:");
            }
            httpPost.setEntity(entity);
        }
        request = httpPost;
    }
    return request;
}
```

### c)、封装的HttpClient与用法

得到了具体的`HttpUriRequest`，就需要使用`HttpClient`对其进行http请求了。
以下是某系统的具体请求过程（基本使用默认配置）。
特别注意几个点：

- httpClient的创建方式和默认配置
- response中entity信息的处理
- httpClient连接管理器的资源释放

```java
/**
 * 执行
 *
 * @param request
 * @param encode
 * @return
 */
private static String execute(HttpUriRequest request, String encode) {
    // 使用默认的httpClient，完全不自定义。类似httpClient的连接管理池、httpRoute路由、httpContext上下文、keepAlive连接超时机制和socket工厂等都是默认初始化的。
    HttpClient httpClient = new DefaultHttpClient();
    httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
    HttpResponse response;
    HttpEntity entity = null;
    InputStream stream = null;
    try {
        // httpClient执行的是httpUriRequest，派生出最常见的是HttpGet和HttpPost
        response = httpClient.execute(request);
        entity = response.getEntity();
        // 特别注意，这里没有使用默认的EntityUtils去做bytes toString，有效的避免了长度超过2048的content尴尬的问题
        stream = entity.getContent();
    } catch (ClientProtocolException e) {
        LoggerUtil.error(logger, e, "客户端异常:");
    } catch (IOException e) {
        LoggerUtil.error(logger, e, "IO异常:");
    }
    StringBuffer document = new StringBuffer();
    String line = null;
    BufferedReader reader;
    try {
        // 流式构造器：带缓冲的入流处理器
        reader = new BufferedReader(new InputStreamReader(stream, encode));
        while ((line = reader.readLine()) != null) {
            document.append(line);
        }
    } catch (UnsupportedEncodingException e) {
        LoggerUtil.error(logger, e, "字符转换异常:");
    } catch (ParseException e) {
        LoggerUtil.error(logger, e, "转换异常:");
    } catch (IOException e) {
        LoggerUtil.error(logger, e, "IO异常:");
    }
    // 连接管理器关闭httpClient连接，释放请求资源
    httpClient.getConnectionManager().shutdown();
    return document.toString();
}
```

以上是使用apache`httpClient`进行http请求的简单方式，下面的内容将会对http协议原理和httpClient实现http协议的原理进行解析。

### d)、解析httpClient结果

如果httpClient请求得到的结果是一个文件，则可以将`entity`的`content`流里的内容写到文件输出流中，这样就可以保存成为文件了，如下：

```java
public static void downloadFile(String url, File downFile) throws IOException {
    HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(url);
    HttpResponse httpResponse = httpClient.execute(httpGet);
    StatusLine statusLine = httpResponse.getStatusLine();
    if (statusLine.getStatusCode() == 200) {
        FileOutputStream outputStream = new FileOutputStream(downFile);
        InputStream inputStream = httpResponse.getEntity().getContent();
        byte buff[] = new byte[4096];
        int counts = 0;
        while ((counts = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, counts);
        }
        outputStream.flush();
        outputStream.close();
    }
    httpClient.getConnectionManager().shutdown();
}
```

# 二、HttpClient常用类介绍

## 1、`HttpEntity`

这是Http请求的实体接口，常用的实现有：`UrlEncodedFormEntity`和`MultipartEntity`两种。前者相当于表单提交的key/value对，用给定的字符集进行编码；后者相当于以二进制流方式来提交表单，一般用来提交文件（当然也可以附带传递，但是跟表单提交值类型不一样）。

### a) 构造`MultipartEntity`

`MultipartEntity`使用`addPart`函数来增加请求的参数。方法签名为：

```java
public void addPart(String name, ContentBody contentBody) {
    this.addPart(new FormBodyPart(name, contentBody));
}
```

其中`ContentBody`类型当是值的时候可以是`StringBody`、也可以是`FileBody`。

如以下是构造`MultipartEntity`类实例、添加参数的过程：

```java
MultipartEntity entity = new MultipartEntity();
try {
    if (paramMap != null && paramMap.size() > 0) {
        for (String key : paramMap.keySet()) {
            entity.addPart(key, new StringBody(paramMap.get(key), Charset.forName(encode)));
        }
    }
} catch (UnsupportedEncodingException e) {
    LoggerUtil.error(logger, e, "字符转换异常:");
}
if (files != null && files.size() > 0) {
    for (File file : files) {
        FileBody fileBody = new FileBody(file, file.getName(), "", encode);
        entity.addPart(file.getName(), fileBody);
    }
}
```

最后这个`MultipartEntity`被塞到`HttpPost`中，如果单纯传送文件一般不会再塞入过多的`StringBody`，可以放到构造的`url`中，也是能接收到参数的。构造`HttpPost`过程如下：

```java
HttpPost httpPost = new HttpPost(url);
httpPost.setEntity(entity);
```

### b) 构造`UrlEncodedFormEntity`

```java
HttpPost httpPost = new HttpPost(url);
if (paramMap != null && paramMap.size() > 0) {
    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    HttpEntity entity = null;
    for (String key : paramMap.keySet()) {
        formParams.add(new BasicNameValuePair(key, paramMap.get(key)));
    }
    try {
        entity = new UrlEncodedFormEntity(formParams, encode);
    } catch (UnsupportedEncodingException e) {
        LoggerUtil.error(logger, e, "字符转换异常:");
    }
    httpPost.setEntity(entity);
}
request = httpPost;
```



# 三、参考文献

以下是HttpClient的参考文献

## 1、Java的HttpClient教程

### a) [HttpClient教程目录](http://www.yeetrack.com/?p=779);

### b) [HttpClient基本概念](http://www.yeetrack.com/?p=773HttpClientAPI);

### c) [HttpClient连接管理](http://www.yeetrack.com/?p=782);

### d) [HttpClient状态管理](http://www.yeetrack.com/?p=822);

### e) [HttpClientHttp认证](http://www.yeetrack.com/?p=825);

### f) [HttpClient快速API](http://www.yeetrack.com/?p=832);

### g) [HttpClientHttp缓存](http://www.yeetrack.com/?p=844http://www.yeetrack.com/?p=844);

## 2、SSL和证书：

[SSLContext与证书配置](http://blog.csdn.net/xiaoxian8023/article/details/49865335);
微信支付的使用证书通信；

## 3、HttpClient自定义

