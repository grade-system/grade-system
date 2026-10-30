<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "成绩查询"); request.setAttribute("active", "scoreList"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <h2>成绩查询</h2>
    <c:if test="${param.msg == 'success'}">
        <div class="alert alert-success">成绩录入成功！</div>
    </c:if>

    <form method="get" class="form-inline" style="margin-bottom: 20px;">
        <div class="form-group">
            <label>课程</label>
            <select name="courseId" required onchange="this.form.submit()">
                <option value="">请选择</option>
                <c:forEach items="${courses}" var="c">
                    <option value="${c.id}" ${courseId == c.id ? 'selected' : ''}>${c.courseName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>班级</label>
            <select name="classId" required onchange="this.form.submit()">
                <option value="">请选择</option>
                <c:forEach items="${classes}" var="c">
                    <option value="${c.id}" ${classId == c.id ? 'selected' : ''}>${c.className}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>考试</label>
            <select name="examName" onchange="this.form.submit()">
                <option value="">请选择</option>
                <c:forEach items="${examNames}" var="e">
                    <option value="${e}" ${examName == e ? 'selected' : ''}>${e}</option>
                </c:forEach>
            </select>
        </div>
    </form>

    <c:if test="${not empty list}">
        <table>
            <thead>
                <tr>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>课程</th>
                    <th>考试</th>
                    <th>成绩</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="s">
                    <tr>
                        <td>${s.studentNo}</td>
                        <td>${s.studentName}</td>
                        <td>${s.courseName}</td>
                        <td>${s.examName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${s.score >= 90}"><span class="score-pass">${s.score}</span></c:when>
                                <c:when test="${s.score >= 60}"><span class="score-pass">${s.score}</span></c:when>
                                <c:when test="${s.score >= 55}"><span class="score-critical">${s.score}</span></c:when>
                                <c:otherwise><span class="score-fail">${s.score}</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/teacher/score?action=delete&id=${s.id}" class="btn btn-sm btn-danger"
                               onclick="return confirm('确定删除该成绩吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty list && not empty courseId}">
        <div class="alert alert-warning">暂无成绩数据</div>
    </c:if>
</div>

<jsp:include page="/common/footer.jsp" />
