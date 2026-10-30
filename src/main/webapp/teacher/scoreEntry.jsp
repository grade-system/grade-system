<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "成绩录入"); request.setAttribute("active", "scoreEntry"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <h2>成绩录入</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="get" class="form-inline" style="margin-bottom: 20px;">
        <input type="hidden" name="action" value="entry">
        <div class="form-group">
            <label>选择课程</label>
            <select name="courseId" required onchange="this.form.submit()">
                <option value="">请选择课程</option>
                <c:forEach items="${courses}" var="c">
                    <option value="${c.id}" ${courseId == c.id ? 'selected' : ''}>${c.courseName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>选择班级</label>
            <select name="classId" required onchange="this.form.submit()">
                <option value="">请选择班级</option>
                <c:forEach items="${classes}" var="c">
                    <option value="${c.id}" ${classId == c.id ? 'selected' : ''}>${c.className}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>考试名称</label>
            <input type="text" name="examName" value="${examName}" placeholder="如：第一次月考"
                   onchange="this.form.submit()">
        </div>
    </form>

    <c:if test="${not empty students}">
        <form action="${pageContext.request.contextPath}/teacher/score" method="post">
            <input type="hidden" name="courseId" value="${courseId}">
            <input type="hidden" name="examName" value="${examName}">
            <table>
                <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>成绩（0-100）</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${students}" var="s">
                        <tr>
                            <td>${s.studentNo}</td>
                            <td>${s.name}</td>
                            <td>
                                <input type="hidden" name="studentId" value="${s.id}">
                                <%-- 回显已有成绩 --%>
                                <c:set var="existScore" value="" />
                                <c:forEach items="${existing}" var="e">
                                    <c:if test="${e.studentId == s.id}">
                                        <c:set var="existScore" value="${e.score}" />
                                    </c:if>
                                </c:forEach>
                                <input type="number" name="score" value="${existScore}"
                                       min="0" max="100" step="0.1"
                                       style="width: 120px; padding: 6px; border: 1px solid #ddd; border-radius: 4px;"
                                       placeholder="请输入分数">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div style="margin-top: 20px;">
                <button type="submit" class="btn btn-success"
                        onclick="return confirm('确认提交成绩吗？重复录入将覆盖已有成绩。')">提交成绩</button>
            </div>
        </form>
    </c:if>

    <c:if test="${empty students && not empty courseId}">
        <div class="alert alert-warning">该班级暂无学生数据</div>
    </c:if>
</div>

<jsp:include page="/common/footer.jsp" />
