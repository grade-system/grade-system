package com.grade.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录拦截 + 角色权限过滤器
 * 规则：
 *   - 放行：/login.jsp、/login、/static/*
 *   - 教师：/teacher/*
 *   - 学生：/student/*
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String ctx = request.getContextPath();
        String uri = request.getRequestURI();

        // 放行：登录页、登录接口、静态资源
        if (uri.endsWith("/login.jsp") || uri.endsWith("/login")
                || uri.startsWith(ctx + "/static/")) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = request.getSession(false);
        String role = null;
        if (session != null) {
            role = (String) session.getAttribute("role");
        }

        // 未登录 → 登录页
        if (role == null) {
            response.sendRedirect(ctx + "/login.jsp");
            return;
        }

        // 角色权限校验
        if (uri.contains("/teacher/") && !"TEACHER".equals(role)) {
            response.sendRedirect(ctx + "/student/myScore");
            return;
        }
        if (uri.contains("/student/") && !"STUDENT".equals(role)) {
            response.sendRedirect(ctx + "/teacher/dashboard");
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
