<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "课程管理"); request.setAttribute("active", "course"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
        <h2 style="margin-bottom: 0;">我的课程</h2>
        <a href="${pageContext.request.contextPath}/teacher/course?action=add" class="btn">+ 新增课程</a>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>课程名称</th>
                <th>授课教师</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="c">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.courseName}</td>
                    <td>${c.teacherName}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/teacher/course?action=edit&id=${c.id}" class="btn btn-sm">编辑</a>
                        <a href="${pageContext.request.contextPath}/teacher/course?action=delete&id=${c.id}" class="btn btn-sm btn-danger"
                           onclick="return confirm('确定删除该课程吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty list}">
                <tr><td colspan="4" style="text-align: center; color: #999;">暂无数据</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="/common/footer.jsp" />
