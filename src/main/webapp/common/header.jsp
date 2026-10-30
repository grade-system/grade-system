<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctx = request.getContextPath();
    String role = (String) session.getAttribute("role");
    String realName = (String) session.getAttribute("realName");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle != null ? pageTitle : '成绩管理系统'}</title>
    <link rel="stylesheet" href="<%=ctx%>/static/css/style.css">
</head>
<body>
<div class="layout">
    <aside class="sidebar">
        <div class="logo">成绩管理系统</div>
        <nav>
            <% if ("TEACHER".equals(role)) { %>
                <a href="<%=ctx%>/teacher/dashboard" class="${active=='dashboard'?'active':''}">首页概览</a>
                <a href="<%=ctx%>/teacher/class" class="${active=='class'?'active':''}">班级管理</a>
                <a href="<%=ctx%>/teacher/course" class="${active=='course'?'active':''}">课程管理</a>
                <a href="<%=ctx%>/teacher/student" class="${active=='student'?'active':''}">学生管理</a>
                <a href="<%=ctx%>/teacher/score?action=entry" class="${active=='scoreEntry'?'active':''}">成绩录入</a>
                <a href="<%=ctx%>/teacher/score" class="${active=='scoreList'?'active':''}">成绩查询</a>
                <a href="<%=ctx%>/teacher/stat" class="${active=='stat'?'active':''}">统计分析</a>
            <% } else { %>
                <a href="<%=ctx%>/student/myScore" class="${active=='myScore'?'active':''}">我的成绩</a>
                <a href="<%=ctx%>/student/myTrend" class="${active=='myTrend'?'active':''}">成绩趋势</a>
            <% } %>
        </nav>
    </aside>
    <div class="main">
        <header class="topbar">
            <span>欢迎您，<%= realName != null ? realName : "" %> （<%= "TEACHER".equals(role) ? "教师" : "学生" %>）</span>
            <a href="<%=ctx%>/logout" class="logout">退出登录</a>
        </header>
        <div class="content">
