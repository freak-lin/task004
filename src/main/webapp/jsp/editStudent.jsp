<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="date" uri="/tags2" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        function sendBtn(telephone) {
            var telephone = document.user.telephone.value;
            var url = '${pageContext.request.contextPath }/message?telephone=' + telephone;

            $.ajax({
                url: url,
                type: 'GET',
            });
        }
    </script>
    <script>window.onload=function() {
        document.getElementById("_submit").onclick = function (event) {
            //取消掉默认的form提交方式
            if (event.preventDefault) event.preventDefault();
            else event.returnValue = false;                           //对于IE的取消方式
            var formDOM = document.getElementsByTagName("form")[0];
            //将form的DOM对象当作FormData的构造函数
            var formData = new FormData(formDOM);
            var req = new XMLHttpRequest();
            req.open("POST", "/signin");
            //请求完成
            req.onload = function () {
                if (this.status === 200) {
                    //对请求成功的处理
                    alert(this.response);
                }
            };
            //将form数据发送出去
            req.send(formData);
            //避免内存泄漏
            req = null;
        }
    }
    </script>
</head>
<body>
<div class="from-group">
    <h1 style="text-align: center">人员信息</h1>
    <hr/>
    <form method="post" name="user">
        <div class="form-group">
            <label for="name">姓名:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="输入姓名"/>
        </div>
        <div class="form-group">
            <label for="gender">QQ:</label>
            <input type="text" class="form-control" id="gender" name="qq" placeholder="QQ"/>
        </div>
        <div class="form-group">
            <label for="country">学习类型:</label>
            <input type="text" class="form-control" id="country" name="learning_type" placeholder="学习类型"/>
        </div>
        <div class="form-group">
            <label for="desire">立愿:</label>
            <input class="form-control" id="desire" name="desire" placeholder="立愿"/>
        </div>
        <div>
        <input type="text" name="telephone" id="telephone" placeholder="请输入手机号码" required="required"/>
            <button type="button" onclick="sendBtn()">获取验证码</button>
            <input type="text" name="verify" placeholder="请输入验证码" required="required">
        </div>

        <div class="form-group">
            <input type="submit" id="_submit" value="提交"/>
        </div>
    </form>
</div>
</body>
</html>
