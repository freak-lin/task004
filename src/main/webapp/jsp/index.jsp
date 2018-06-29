<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="date" uri="/tags2" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>学生表单</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        function sendBtn(id) {
            var url = '${pageContext.request.contextPath }/stu/' + id;/*得到href的值*/
            $.ajax({
                url:url,/*url也可以是json之类的文件等等*/
                type:'DELETE',/*DELETE、POST */
                success:function (result) {
                    //判断result结果
                    if(result){
                        alert("id: " + id + "删除成功,即将返回列表页")
                        window.location.reload();
                    }else{
                        alert("id: " + id + " 删除失败")
                    }
                }
            });
        }
    </script>
</head>
<body>
<fieldset>
    <a href="<c:url value="${pageContext.request.contextPath}/stu" />">添加</a>
</fieldset>
<%--${pageContext.request.contextPath}是绝对路径--%>
<form action="${pageContext.request.contextPath}/stu" method="get">
    <label for="name">姓名：</label>
    <input id="name" name="name">
    <input type="submit" value="查询"/>
</form>
<table width="100%" border=1>
    <thead>
    <tr>
        <td>ID</td>
        <td>用户名</td>
        <td>QQ</td>
        <%--<td>入学时间</td>--%>
        <td>学习类型</td>
        <td>学号</td>
        <td>立愿</td>
        <td>创建时间</td>
        <td>更新时间</td>
        <td>删除</td>
        <td>修改</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${studentList}" var="infos">
        <tr>
            <td> ${infos.id}</td>
            <td>${infos.name }</td>
            <td>${infos.qq}</td>
            <%--<td><fmt:formatDate value="${infos.enrolment_time}" type="date"/> </td>--%>
            <td>${infos.learning_type}</td>
            <td>${infos.number}</td>
            <td>${infos.desire}</td>
            <td><date:date value="${infos.create_at}"/></td>
            <td><date:date value="${infos.update_at}"/></td>

            <td><a href="${pageContext.request.contextPath}/index" onclick="sendBtn(${infos.id})" >删除</a>
            </td>
            <td><a href="${pageContext.request.contextPath}/student/${infos.id}">edit</a> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
