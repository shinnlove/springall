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

<br/>
<h2>ajax方式提交文件</h2>
<div>
    <form name="tony">
        <input type="file" name="ajax-file"/>
        <input type="button" name="ajax-btn" value="ajax上传页面不跳转"/>
    </form>
</div>
<div class="result">
    <img id="preview" src=""/>
</div>

<!-- 找不到jQuery，写一段风骚的JS，别用IE，没时间做兼容哈~ -->
<script type="text/javascript">
    document.querySelector("input[name='ajax-btn']").onclick = function (e) {

        // DIY url...
        var projectName = "springall";
        var uploadURL = "/" + projectName + "/ajax/singleUpload";

        // check and get file
        var fileList = document.querySelector("input[name='ajax-file']").files;
        if (!fileList.length) {
            alert("请选择文件上传");
            return false; // 没有文件返回
        }
        var file = fileList[0];

        var errorCallback = function (e) {
            alert("请求出错或被中断，message=" + JSON.stringify(e));
        };

        var completed = function (e) {
            var result = JSON.parse(e.target.response);
            if (result.errCode == 0) {
                document.querySelector("#preview").src = result.data.fileURL;
                alert("文件上传成功，fileId=" + result.data.fileId + ", fileURL=" + result.data.fileURL);
            } else {
                alert("啊哦，上传文件失败了，错误信息ex=" + result.errMsg);
            }
        }

        //创建表单数据
        var formData = new FormData();
        formData.append('file', file);
        // 创建 ajax 请求
        var xhr = new XMLHttpRequest();
        xhr.addEventListener("load", completed, false);		        //监听XMLHttpRequest的load事件，使用模块内complete函数响应
        xhr.addEventListener("error", errorCallback, false);		//监听XMLHttpRequest的error事件，使用模块内failed函数响应
        xhr.open("POST", uploadURL + '?time=' + Date.now());
        xhr.send(formData);

    };
</script>
</body>
</html>
