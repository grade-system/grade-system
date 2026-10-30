<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "班级管理"); request.setAttribute("active", "class"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
        <h2 style="margin-bottom: 0;">班级列表</h2>
        <a href="${pageContext.request.contextPath}/teacher/class?action=add" class="btn">+ 新增班级</a>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>班级名称</th>
                <th>年级</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="c">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.className}</td>
                    <td>${c.grade}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/teacher/class?action=edit&id=${c.id}" class="btn btn-sm">编辑</a>
                        <a href="${pageContext.request.contextPath}/teacher/class?action=delete&id=${c.id}" class="btn btn-sm btn-danger"
                           onclick="return confirm('确定删除该班级吗？')">删除</a>
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
