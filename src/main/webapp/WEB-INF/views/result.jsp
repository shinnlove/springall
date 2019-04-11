<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传成功</title>
</head>
<body>
<div>
    文件上传成功，路径为：${filePath}，<br/>
    <img alt="" src="${filePath}"/>
</div>
<div>
    <!--注意action提交的路径不能/开头，必须是相对路径，而且不需要前面的路径-->
    <form action="downloadFile" method="get">
        <!-- 特别注意：前端提交的时候必须把文件id带上来，查询数据库得到filePath，这里没有数据库直接默认刚上传路径就是查出来的值 -->
        <input type="hidden" name="fileId" value="123456"/>
        <input type="hidden" name="filePath" value="${filePath}"/>
        <input type="button" name="ajaxBtn" value="ajax方式-下载文件"/>
        <input type="submit" value="表单提交方式-下载文件"/>
    </form>
</div>
<script type="text/javascript">
    // 写一段风骚的原生js做演示
    document.querySelector("input[name='ajaxBtn']").onclick = function () {
        // 必须要前端同学传过来的fileId，查数据库用
        var fileId = document.querySelector("input[name='fileId']").value;
        // 做演示传路径
        var filePath = document.querySelector("input[name='filePath']").value;

        // 请前端同学定义成本项目名
        var projectName = "springall";
        // filePath这个字段生产中不用传，做演示用
        window.open("/" + projectName + "/upload/downloadFile?fileId=" + fileId + "&filePath=" + encodeURIComponent(filePath));
    }
</script>
</body>
</html>
