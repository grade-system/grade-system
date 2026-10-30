package com.grade.servlet;

import com.grade.entity.Score;
import com.grade.entity.Student;
import com.grade.service.ClassService;
import com.grade.service.CourseService;
import com.grade.service.ScoreService;
import com.grade.service.StudentService;
import com.grade.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 成绩管理 Servlet（教师录入 + 查询）
 */
@WebServlet("/teacher/score")
public class ScoreServlet extends HttpServlet {

    private final ScoreService scoreService = new ScoreService();
    private final CourseService courseService = new CourseService();
    private final ClassService classService = new ClassService();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long teacherId = (Long) session.getAttribute("userId");

        String action = req.getParameter("action");

        if ("entry".equals(action)) {
            // 成绩录入页：选择课程、班级后展示学生列表
            Long courseId = parseLong(req.getParameter("courseId"));
            Long classId = parseLong(req.getParameter("classId"));
            String examName = req.getParameter("examName");

            req.setAttribute("courses", courseService.findByTeacherId(teacherId));
            req.setAttribute("classes", classService.findAll());
            req.setAttribute("courseId", courseId);
            req.setAttribute("classId", classId);
            req.setAttribute("examName", examName);

            if (courseId != null && classId != null) {
                // 查该班学生列表
                PageUtil page = studentService.getPage(1, 100, classId);
                List<Student> students = studentService.findByPage(page, classId);
                req.setAttribute("students", students);
                // 查已有成绩（用于回显）
                List<Score> existing = scoreService.findScores(courseId, classId, examName);
                req.setAttribute("existing", existing);
            }
            req.getRequestDispatcher("/teacher/scoreEntry.jsp").forward(req, resp);
            return;
        }

        if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            scoreService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/teacher/score");
            return;
        }

        // 默认：成绩查询列表
        Long courseId = parseLong(req.getParameter("courseId"));
        Long classId = parseLong(req.getParameter("classId"));
        String examName = req.getParameter("examName");

        req.setAttribute("courses", courseService.findByTeacherId(teacherId));
        req.setAttribute("classes", classService.findAll());
        req.setAttribute("examNames", scoreService.findExamNames());
        req.setAttribute("courseId", courseId);
        req.setAttribute("classId", classId);
        req.setAttribute("examName", examName);

        if (courseId != null && classId != null && examName != null && !examName.isEmpty()) {
            req.setAttribute("list", scoreService.findScores(courseId, classId, examName));
        }
        req.getRequestDispatcher("/teacher/scoreList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 批量录入成绩
        Long courseId = Long.parseLong(req.getParameter("courseId"));
        String examName = req.getParameter("examName");
        String[] studentIds = req.getParameterValues("studentId");
        String[] scores = req.getParameterValues("score");

        List<Score> list = new ArrayList<>();
        for (int i = 0; i < studentIds.length; i++) {
            String scoreStr = scores[i];
            if (scoreStr == null || scoreStr.trim().isEmpty()) continue;
            Score s = new Score();
            s.setStudentId(Long.parseLong(studentIds[i]));
            s.setCourseId(courseId);
            s.setExamName(examName);
            s.setScore(new BigDecimal(scoreStr.trim()));
            list.add(s);
        }

        try {
            scoreService.batchSave(list);
            resp.sendRedirect(req.getContextPath() + "/teacher/score?msg=success");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }

    private Long parseLong(String s) {
        try { return s == null || s.isEmpty() ? null : Long.parseLong(s); } catch (Exception e) { return null; }
    }
}
