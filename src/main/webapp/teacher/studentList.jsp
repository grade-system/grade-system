<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "学生管理"); request.setAttribute("active", "student"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
        <h2 style="margin-bottom: 0;">学生列表</h2>
        <a href="${pageContext.request.contextPath}/teacher/student?action=add" class="btn">+ 新增学生</a>
    </div>

    <form method="get" style="margin-bottom: 15px;" class="form-inline">
        <div class="form-group">
            <label>按班级筛选</label>
            <select name="classId" onchange="this.form.submit()">
                <option value="">全部班级</option>
                <c:forEach items="${classes}" var="c">
                    <option value="${c.id}" ${classId == c.id ? 'selected' : ''}>${c.className}</option>
                </c:forEach>
            </select>
        </div>
    </form>

    <table>
        <thead>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>班级</th>
                <th>年级</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="s">
                <tr>
                    <td>${s.studentNo}</td>
                    <td>${s.name}</td>
                    <td>${s.className}</td>
                    <td>${s.grade}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/teacher/student?action=edit&id=${s.id}" class="btn btn-sm">编辑</a>
                        <a href="${pageContext.request.contextPath}/teacher/student?action=delete&id=${s.id}" class="btn btn-sm btn-danger"
                           onclick="return confirm('确定删除该学生吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty list}">
                <tr><td colspan="5" style="text-align: center; color: #999;">暂无数据</td></tr>
            </c:if>
        </tbody>
    </table>

    <c:if test="${page.totalPages > 1}">
        <div class="pagination">
            <c:if test="${page.hasPrev()}">
                <a href="?page=${page.pageNum - 1}&classId=${classId}">上一页</a>
            </c:if>
            <span class="current">${page.pageNum} / ${page.totalPages}</span>
            <c:if test="${page.hasNext()}">
                <a href="?page=${page.pageNum + 1}&classId=${classId}">下一页</a>
            </c:if>
            <span>共 ${page.total} 条</span>
        </div>
    </c:if>
</div>

<jsp:include page="/common/footer.jsp" />
