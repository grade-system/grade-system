<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "成绩趋势"); request.setAttribute("active", "myTrend"); %>
<jsp:include page="/common/header.jsp" />

<c:if test="${not empty student}">
    <div class="card">
        <h2>成绩趋势分析 - ${student.name}</h2>

        <form method="get" class="form-inline" style="margin-bottom: 20px;">
            <div class="form-group">
                <label>选择课程</label>
                <select name="courseId" onchange="this.form.submit()">
                    <c:forEach items="${courses}" var="c">
                        <option value="${c.id}" ${courseId == c.id ? 'selected' : ''}>${c.courseName}</option>
                    </c:forEach>
                </select>
            </div>
        </form>

        <c:if test="${not empty trend}">
            <div id="trendChart" style="width: 100%; height: 380px;"></div>

            <h3 style="margin: 25px 0 10px; color: #2c3e50;">历次考试详情</h3>
            <table>
                <thead>
                    <tr>
                        <th>考试名称</th>
                        <th>我的成绩</th>
                        <th>班级平均分</th>
                        <th>班级排名</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${trend}" var="t">
                        <tr>
                            <td>${t.examName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${t.score >= 60}"><span class="score-pass">${t.score}</span></c:when>
                                    <c:otherwise><span class="score-fail">${t.score}</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>${t.classAvg}</td>
                            <td>第 ${t.rank} 名</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty trend}">
            <div class="alert alert-warning">暂无该课程的成绩数据</div>
        </c:if>
    </div>
</c:if>

<c:if test="${not empty trend}">
<script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
<script>
    var chart = echarts.init(document.getElementById('trendChart'));
    var exams = [
        <c:forEach items="${trend}" var="t" varStatus="st">'${t.examName}'${!st.last ? ',' : ''}</c:forEach>
    ];
    var myScores = [
        <c:forEach items="${trend}" var="t" varStatus="st">${t.score}${!st.last ? ',' : ''}</c:forEach>
    ];
    var classAvgs = [
        <c:forEach items="${trend}" var="t" varStatus="st">${t.classAvg}${!st.last ? ',' : ''}</c:forEach>
    ];

    var option = {
        title: { text: '个人成绩 vs 班级平均分 趋势', left: 'center' },
        tooltip: { trigger: 'axis' },
        legend: { data: ['我的成绩', '班级平均分'], bottom: 0 },
        xAxis: { type: 'category', data: exams },
        yAxis: { type: 'value', min: 0, max: 100, name: '分数' },
        series: [
            {
                name: '我的成绩',
                type: 'line',
                data: myScores,
                smooth: true,
                itemStyle: { color: '#3498db' },
                lineStyle: { width: 3 },
                markPoint: {
                    data: [
                        { type: 'max', name: '最高分' },
                        { type: 'min', name: '最低分' }
                    ]
                }
            },
            {
                name: '班级平均分',
                type: 'line',
                data: classAvgs,
                smooth: true,
                itemStyle: { color: '#e67e22' },
                lineStyle: { width: 2, type: 'dashed' }
            }
        ]
    };
    chart.setOption(option);
    window.addEventListener('resize', function() { chart.resize(); });
</script>
</c:if>

<jsp:include page="/common/footer.jsp" />
