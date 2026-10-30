<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("pageTitle", "课程编辑"); request.setAttribute("active", "course"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <h2>${course == null ? '新增课程' : '编辑课程'}</h2>
    <form action="${pageContext.request.contextPath}/teacher/course" method="post" style="max-width: 500px;">
        <input type="hidden" name="id" value="${course.id}">
        <div class="form-group">
            <label>课程名称</label>
            <input type="text" name="courseName" value="${course.courseName}" required placeholder="如：Java程序设计">
        </div>
        <div class="form-group">
            <label>授课教师</label>
            <input type="text" value="${sessionScope.realName}" disabled>
            <p style="font-size: 12px; color: #999; margin-top: 4px;">课程自动归属当前登录教师</p>
        </div>
        <div style="margin-top: 20px;">
            <button type="submit" class="btn btn-success">保存</button>
            <a href="${pageContext.request.contextPath}/teacher/course" class="btn btn-danger">取消</a>
        </div>
    </form>
</div>

<jsp:include page="/common/footer.jsp" />
