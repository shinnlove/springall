<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>上传文件</title>
</head>

<body>
<h2>单个文件上传</h2>
<div>
    <!--注意action提交的路径不能/开头，必须是相对路径，而且不需要前面的路径-->
    <form action="singleUpload" method="post" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit" value="单个文件上传"/>
    </form>
</div>

<br/>
<h2>多个文件上传</h2>
<div>
    <form action="multipartUpload" method="post" enctype="multipart/form-data">
        <!-- 上传多个文件name一致，get的时候就会得到数组 -->
        <input type="file" name="product-image"/>
        <input type="file" name="product-image"/>
        <input type="file" name="product-image"/>
        <input type="submit" value="多个文件上传"/>
    </form>
</div>
</body>
</html>
