<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "统计分析"); request.setAttribute("active", "stat"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <h2>班级成绩统计分析</h2>

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

    <c:if test="${not empty stat}">
        <div class="stat-grid">
            <div class="stat-card avg">
                <div class="label">平均分</div>
                <div class="value">${stat.avgScore}</div>
            </div>
            <div class="stat-card">
                <div class="label">最高分</div>
                <div class="value">${stat.maxScore}</div>
            </div>
            <div class="stat-card">
                <div class="label">最低分</div>
                <div class="value">${stat.minScore}</div>
            </div>
            <div class="stat-card pass">
                <div class="label">及格率</div>
                <div class="value">${stat.passRate}<span class="unit">%</span></div>
            </div>
            <div class="stat-card excellent">
                <div class="label">优秀率(≥90)</div>
                <div class="value">${stat.excellentRate}<span class="unit">%</span></div>
            </div>
            <div class="stat-card std">
                <div class="label">标准差</div>
                <div class="value">${stat.stdDev}</div>
            </div>
        </div>

        <div style="background: #f8f9fa; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; color: #666;">
            <strong>${stat.className}</strong> - <strong>${stat.courseName}</strong> - <strong>${stat.examName}</strong>
            ，参考人数：<strong>${stat.totalStudents}</strong> 人
            <c:if test="${stat.stdDev != null}">
                ，标准差 <strong>${stat.stdDev}</strong>
                <c:choose>
                    <c:when test="${stat.stdDev < 8}">（成绩分布较集中，学生差距小）</c:when>
                    <c:when test="${stat.stdDev < 15}">（成绩分布适中）</c:when>
                    <c:otherwise>（成绩分布较分散，两极分化明显）</c:otherwise>
                </c:choose>
            </c:if>
        </div>

        <%-- 成绩分布柱状图 --%>
        <div id="chart" style="width: 100%; height: 320px; margin-bottom: 20px;"></div>

        <%-- 临界生表格 --%>
        <h3 style="margin: 20px 0 10px; color: #e67e22;">⚠️ 临界生名单（55-59分，需重点关注）</h3>
        <c:if test="${not empty criticalList}">
            <table>
                <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>班级</th>
                        <th>课程</th>
                        <th>考试</th>
                        <th>分数</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${criticalList}" var="cs">
                        <tr>
                            <td>${cs.studentNo}</td>
                            <td>${cs.studentName}</td>
                            <td>${cs.className}</td>
                            <td>${cs.courseName}</td>
                            <td>${cs.examName}</td>
                            <td><span class="score-critical">${cs.score}</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty criticalList}">
            <div class="alert alert-success">本次考试无临界生，整体表现良好！</div>
        </c:if>
    </c:if>

    <c:if test="${empty stat && not empty courseId}">
        <div class="alert alert-warning">暂无统计数据，请确认已录入成绩</div>
    </c:if>
</div>

<c:if test="${not empty stat}">
<script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
<script>
    var chart = echarts.init(document.getElementById('chart'));
    var option = {
        title: { text: '成绩指标对比', left: 'center' },
        tooltip: { trigger: 'axis' },
        legend: { data: ['分数', '百分比(%)'], bottom: 0 },
        xAxis: {
            type: 'category',
            data: ['平均分', '最高分', '最低分', '及格率', '优秀率', '标准差']
        },
        yAxis: [
            { type: 'value', name: '分数', max: 100 },
            { type: 'value', name: '百分比(%)', max: 100 }
        ],
        series: [
            {
                name: '分数',
                type: 'bar',
                data: [${stat.avgScore}, ${stat.maxScore}, ${stat.minScore}, null, null, ${stat.stdDev}],
                itemStyle: { color: '#3498db' }
            },
            {
                name: '百分比(%)',
                type: 'bar',
                yAxisIndex: 1,
                data: [null, null, null, ${stat.passRate}, ${stat.excellentRate}, null],
                itemStyle: { color: '#2ecc71' }
            }
        ]
    };
    chart.setOption(option);
    window.addEventListener('resize', function() { chart.resize(); });
</script>
</c:if>

<jsp:include page="/common/footer.jsp" />
