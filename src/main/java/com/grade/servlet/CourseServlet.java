package com.grade.servlet;

import com.grade.entity.Course;
import com.grade.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 课程管理 Servlet
 */
@WebServlet("/teacher/course")
public class CourseServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long teacherId = (Long) session.getAttribute("userId");

        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("course", courseService.findById(id));
            req.getRequestDispatcher("/teacher/courseEdit.jsp").forward(req, resp);
            return;
        }
        if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            courseService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/teacher/course");
            return;
        }
        if ("add".equals(action)) {
            req.getRequestDispatcher("/teacher/courseEdit.jsp").forward(req, resp);
            return;
        }
        // 教师只看自己的课程
        req.setAttribute("list", courseService.findByTeacherId(teacherId));
        req.getRequestDispatcher("/teacher/courseList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long teacherId = (Long) session.getAttribute("userId");

        Course c = new Course();
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            c.setId(Long.parseLong(idStr));
        }
        c.setCourseName(req.getParameter("courseName"));
        c.setTeacherId(teacherId); // 课程归属当前登录教师
        courseService.save(c);
        resp.sendRedirect(req.getContextPath() + "/teacher/course");
    }
}
