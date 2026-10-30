package com.grade.servlet;

import com.grade.entity.Student;
import com.grade.service.ClassService;
import com.grade.service.StudentService;
import com.grade.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 学生管理 Servlet
 */
@WebServlet("/teacher/student")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final ClassService classService = new ClassService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("student", studentService.findById(id));
            req.setAttribute("classes", classService.findAll());
            req.getRequestDispatcher("/teacher/studentEdit.jsp").forward(req, resp);
            return;
        }
        if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            studentService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/teacher/student");
            return;
        }
        if ("add".equals(action)) {
            req.setAttribute("classes", classService.findAll());
            req.getRequestDispatcher("/teacher/studentEdit.jsp").forward(req, resp);
            return;
        }

        // 列表（支持按班级筛选 + 分页）
        int pageNum = parseInt(req.getParameter("page"), 1);
        Long classId = parseLong(req.getParameter("classId"));

        PageUtil page = studentService.getPage(pageNum, 8, classId);
        req.setAttribute("list", studentService.findByPage(page, classId));
        req.setAttribute("page", page);
        req.setAttribute("classId", classId);
        req.setAttribute("classes", classService.findAll());
        req.getRequestDispatcher("/teacher/studentList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Student s = new Student();
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            s.setId(Long.parseLong(idStr));
        }
        s.setStudentNo(req.getParameter("studentNo"));
        s.setName(req.getParameter("name"));
        s.setClassId(Long.parseLong(req.getParameter("classId")));
        studentService.save(s);
        resp.sendRedirect(req.getContextPath() + "/teacher/student");
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
    private Long parseLong(String s) {
        try { return s == null || s.isEmpty() ? null : Long.parseLong(s); } catch (Exception e) { return null; }
    }
}
