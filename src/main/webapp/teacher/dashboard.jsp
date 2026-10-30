<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("pageTitle", "首页概览"); request.setAttribute("active", "dashboard"); %>
<jsp:include page="/common/header.jsp" />

<div class="stat-grid">
    <div class="stat-card">
        <div class="label">班级总数</div>
        <div class="value">${classCount}</div>
    </div>
    <div class="stat-card">
        <div class="label">我的课程</div>
        <div class="value">${courseCount}</div>
    </div>
    <div class="stat-card">
        <div class="label">学生总数</div>
        <div class="value">${studentCount}</div>
    </div>
</div>

<div class="card">
    <h2>快捷操作</h2>
    <div style="display: flex; gap: 12px; flex-wrap: wrap;">
        <a href="${pageContext.request.contextPath}/teacher/score?action=entry" class="btn">📝 录入成绩</a>
        <a href="${pageContext.request.contextPath}/teacher/stat" class="btn btn-success">📊 统计分析</a>
        <a href="${pageContext.request.contextPath}/teacher/student" class="btn btn-warning">👥 学生管理</a>
        <a href="${pageContext.request.contextPath}/teacher/course" class="btn">📚 课程管理</a>
    </div>
</div>

<div class="card">
    <h2>系统说明</h2>
    <p style="color: #666; line-height: 1.8; font-size: 14px;">
        本系统支持：教师录入成绩、学生查询成绩、班级统计分析（平均分/及格率/标准差/优秀率）、
        临界生识别（55-59分）、学生个人成绩趋势分析等功能。
    </p>
</div>

<jsp:include page="/common/footer.jsp" />
