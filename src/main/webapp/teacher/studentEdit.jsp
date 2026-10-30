<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "学生编辑"); request.setAttribute("active", "student"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <h2>${student == null ? '新增学生' : '编辑学生'}</h2>
    <form action="${pageContext.request.contextPath}/teacher/student" method="post" style="max-width: 500px;">
        <input type="hidden" name="id" value="${student.id}">
        <div class="form-group">
            <label>学号</label>
            <input type="text" name="studentNo" value="${student.studentNo}" required>
        </div>
        <div class="form-group">
            <label>姓名</label>
            <input type="text" name="name" value="${student.name}" required>
        </div>
        <div class="form-group">
            <label>所属班级</label>
            <select name="classId" required>
                <option value="">请选择班级</option>
                <c:forEach items="${classes}" var="c">
                    <option value="${c.id}" ${student.classId == c.id ? 'selected' : ''}>${c.className}</option>
                </c:forEach>
            </select>
        </div>
        <div style="margin-top: 20px;">
            <button type="submit" class="btn btn-success">保存</button>
            <a href="${pageContext.request.contextPath}/teacher/student" class="btn btn-danger">取消</a>
        </div>
    </form>
</div>

<jsp:include page="/common/footer.jsp" />
