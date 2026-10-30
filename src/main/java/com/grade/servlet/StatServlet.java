package com.grade.servlet;

import com.grade.dto.ClassStatVO;
import com.grade.dto.CriticalStudentVO;
import com.grade.dto.TrendPointVO;
import com.grade.service.ClassService;
import com.grade.service.CourseService;
import com.grade.service.ScoreService;
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
 * 统计分析 Servlet（班级统计 + 临界生 + 学生趋势）
 */
@WebServlet("/teacher/stat")
public class StatServlet extends HttpServlet {

    private final StatService statService = new StatService();
    private final CourseService courseService = new CourseService();
    private final ClassService classService = new ClassService();
    private final ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long teacherId = (Long) session.getAttribute("userId");

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
            // 1. 班级统计（平均分、及格率、标准差等）
            ClassStatVO stat = statService.getClassStat(courseId, classId, examName);
            req.setAttribute("stat", stat);

            // 2. 临界生列表（55-59分）
            List<CriticalStudentVO> critical = statService.findCriticalStudents(courseId, classId, examName);
            req.setAttribute("criticalList", critical);
        }

        req.getRequestDispatcher("/teacher/classStat.jsp").forward(req, resp);
    }

    private Long parseLong(String s) {
        try { return s == null || s.isEmpty() ? null : Long.parseLong(s); } catch (Exception e) { return null; }
    }
}
