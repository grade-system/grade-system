package com.grade.servlet;

import com.grade.entity.ClassInfo;
import com.grade.service.ClassService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 班级管理 Servlet
 */
@WebServlet("/teacher/class")
public class ClassServlet extends HttpServlet {

    private final ClassService classService = new ClassService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("cls", classService.findById(id));
            req.getRequestDispatcher("/teacher/classEdit.jsp").forward(req, resp);
            return;
        }
        if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            classService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/teacher/class");
            return;
        }
        if ("add".equals(action)) {
            req.getRequestDispatcher("/teacher/classEdit.jsp").forward(req, resp);
            return;
        }
        // 默认：列表
        req.setAttribute("list", classService.findAll());
        req.getRequestDispatcher("/teacher/classList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ClassInfo c = new ClassInfo();
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            c.setId(Long.parseLong(idStr));
        }
        c.setClassName(req.getParameter("className"));
        c.setGrade(req.getParameter("grade"));
        classService.save(c);
        resp.sendRedirect(req.getContextPath() + "/teacher/class");
    }
}
