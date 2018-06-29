<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="date" uri="/tags2" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>window.onload=function() {
        document.getElementById("_submit").onclick = function (event) {
            //取消掉默认的form提交方式
            if (event.preventDefault) event.preventDefault();
            else event.returnValue = false;                           //对于IE的取消方式
            var formDOM = document.getElementsByTagName("form")[0];
            //将form的DOM对象当作FormData的构造函数
            var formData = new FormData(formDOM);
            formData.append("id",${student.id});
            var req = new XMLHttpRequest();
            req.open("POST", "/updaImage");
            //请求完成
            req.onload = function () {
                if (this.status === 200) {
                    //对请求成功的处理
                    var img = document.createElement("img");
                    img.src = this.response;
                    img.width = 50;
                    formDOM.insertBefore(img, document.getElementById("_submit"));
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
<div>
    <table width="100%" border=1>
        <thead>
        <tr>
            <th>基本资料</th>
        </tr>
        <tr>
            <td>ID</td>
            <td>用户名</td>
            <td>QQ</td>
            <%--<td>入学时间</td>--%>
            <td>学习类型</td>
            <td>立愿</td>
            <td>创建时间</td>
            <td>更新时间</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td> ${student.id}</td>
            <td>${student.name }</td>
            <td>${student.qq}</td>
            <%--<td><fmt:formatDate value="${student.enrolment_time}" type="date"/> </td>--%>
            <td>${student.learning_type}</td>
            <td>${student.desire}</td>
            <td><date:date value="${student.create_at}"/></td>
            <td><date:date value="${student.update_at}"/></td>
        </tr>
        </tbody>
    </table>
</div>
<div>
    <table>
        <tr>
            <th>头像上传</th>
        </tr>
        <tr>
            <td>
                <form method="POST" id = "form" action="${pageContext.request.contextPath}/updaImage" enctype="multipart/form-data">
                    头像 <input type="file" name="file"/>
                    <input type="submit" id="_submit" value="提交"/>
                </form>
            </td>
        </tr>
    </table>
</div>
<div>
    <table>
        <tr>
            <th>邮箱验证</th>
        </tr>
        <tr>
            <td>
                <form method="POST" id = "mail" action="${pageContext.request.contextPath}/sendMail">
                    <input type="number" name="id" id="id" value=${student.id} />
                    邮箱 <input type="email" name="mail"/>
                    <input type="submit" id="sumit2" value="提交"/>
                </form>
                <iframe id="id_iframe" name="nm_iframe" style="display:none;"></iframe>
            </td>
            <td>
                邮箱激活状态:  ${student.mailboxeVrification}
            </td>
        </tr>
    </table>
</div>
</body>
</html>
