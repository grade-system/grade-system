<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录 - 班级学生成绩管理系统</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-box {
            background: #fff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.2);
            width: 380px;
        }
        .login-box h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .login-box p.subtitle {
            text-align: center;
            color: #888;
            font-size: 13px;
            margin-bottom: 25px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            color: #555;
            margin-bottom: 6px;
            font-size: 14px;
        }
        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        .btn {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: opacity 0.3s;
        }
        .btn:hover { opacity: 0.9; }
        .error {
            color: #e74c3c;
            font-size: 13px;
            text-align: center;
            margin-bottom: 15px;
            background: #fdf0ef;
            padding: 8px;
            border-radius: 4px;
        }
        .tips {
            margin-top: 20px;
            padding: 12px;
            background: #f5f7fa;
            border-radius: 6px;
            font-size: 12px;
            color: #666;
            line-height: 1.8;
        }
        .tips code {
            background: #eef2f7;
            padding: 2px 6px;
            border-radius: 3px;
            color: #667eea;
        }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>班级学生成绩管理系统</h2>
        <p class="subtitle">教师录入 / 学生查询 / 成绩统计分析</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>账号</label>
                <input type="text" name="username" placeholder="请输入账号" required>
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" placeholder="请输入密码" required>
            </div>
            <button type="submit" class="btn">登 录</button>
        </form>

        <div class="tips">
            <strong>测试账号（密码均为 123456）：</strong><br>
            教师：<code>teacher01</code> / <code>teacher02</code><br>
            学生：<code>student01</code> / <code>student07</code>
        </div>
    </div>
</body>
</html>
