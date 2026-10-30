<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "我的成绩"); request.setAttribute("active", "myScore"); %>
<jsp:include page="/common/header.jsp" />

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<c:if test="${not empty student}">
    <div class="card">
        <h2>我的成绩 - ${student.name}（${student.studentNo}）</h2>
        <p style="color: #666; margin-bottom: 15px;">班级：${student.className} | 年级：${student.grade}</p>

        <c:forEach items="${groupedScores}" var="entry">
            <h3 style="margin: 20px 0 10px; color: #2c3e50; border-left: 3px solid #3498db; padding-left: 10px;">
                📝 ${entry.key}
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>课程</th>
                        <th>成绩</th>
                        <th>等级</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="total" value="0" />
                    <c:set var="count" value="0" />
                    <c:forEach items="${entry.value}" var="s">
                        <tr>
                            <td>${s.courseName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.score >= 90}"><span class="score-pass">${s.score}（优秀）</span></c:when>
                                    <c:when test="${s.score >= 60}"><span class="score-pass">${s.score}（及格）</span></c:when>
                                    <c:when test="${s.score >= 55}"><span class="score-critical">${s.score}（临界）</span></c:when>
                                    <c:otherwise><span class="score-fail">${s.score}（不及格）</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.score >= 90}">A</c:when>
                                    <c:when test="${s.score >= 80}">B</c:when>
                                    <c:when test="${s.score >= 70}">C</c:when>
                                    <c:when test="${s.score >= 60}">D</c:when>
                                    <c:otherwise>F</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:set var="total" value="${total + s.score}" />
                        <c:set var="count" value="${count + 1}" />
                    </c:forEach>
                    <tr style="background: #f8f9fa; font-weight: bold;">
                        <td>平均分</td>
                        <td colspan="2">
                            <c:choose>
                                <c:when test="${count > 0}">
                                    ${String.format('%.2f', total / count)}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </c:forEach>

        <c:if test="${empty groupedScores}">
            <div class="alert alert-warning">暂无成绩数据</div>
        </c:if>
    </div>
</c:if>

<jsp:include page="/common/footer.jsp" />
