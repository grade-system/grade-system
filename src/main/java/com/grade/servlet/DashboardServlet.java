package com.grade.servlet;

import com.grade.service.ClassService;
import com.grade.service.CourseService;
import com.grade.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 教师首页 Servlet（数据概览）
 */
@WebServlet("/teacher/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ClassService classService = new ClassService();
    private final CourseService courseService = new CourseService();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long teacherId = (Long) session.getAttribute("userId");

        req.setAttribute("classCount", classService.findAll().size());
        req.setAttribute("courseCount", courseService.findByTeacherId(teacherId).size());
        req.setAttribute("studentCount", studentService.getPage(1, 10, null).getTotal());

        req.getRequestDispatcher("/teacher/dashboard.jsp").forward(req, resp);
    }
}
