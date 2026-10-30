package com.grade.servlet;

import com.grade.dto.TrendPointVO;
import com.grade.entity.Course;
import com.grade.entity.Student;
import com.grade.service.CourseService;
import com.grade.service.StudentService;
import com.grade.service.StatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 学生端 - 成绩趋势 Servlet
 */
@WebServlet("/student/myTrend")
public class MyTrendServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final StatService statService = new StatService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");

        Student student = studentService.findByUserId(userId);
        List<Course> courses = courseService.findAll();

        Long courseId = parseLong(req.getParameter("courseId"));
        if (courseId == null && !courses.isEmpty()) {
            courseId = courses.get(0).getId();
        }

        req.setAttribute("student", student);
        req.setAttribute("courses", courses);
        req.setAttribute("courseId", courseId);

        if (student != null && courseId != null) {
            List<TrendPointVO> trend = statService.getStudentTrend(student.getId(), courseId);
            req.setAttribute("trend", trend);
        }

        req.getRequestDispatcher("/student/myTrend.jsp").forward(req, resp);
    }

    private Long parseLong(String s) {
        try { return s == null || s.isEmpty() ? null : Long.parseLong(s); } catch (Exception e) { return null; }
    }
}
