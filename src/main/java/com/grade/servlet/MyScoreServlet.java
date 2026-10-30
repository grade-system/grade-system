package com.grade.servlet;

import com.grade.entity.Score;
import com.grade.entity.Student;
import com.grade.service.ScoreService;
import com.grade.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 学生端 - 我的成绩 Servlet
 */
@WebServlet("/student/myScore")
public class MyScoreServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");

        Student student = studentService.findByUserId(userId);
        if (student == null) {
            req.setAttribute("error", "未找到学生信息");
            req.getRequestDispatcher("/student/myScore.jsp").forward(req, resp);
            return;
        }

        List<Score> scores = scoreService.findByStudentId(student.getId());

        // 按考试名称分组，便于 JSP 展示
        Map<String, List<Score>> grouped = new TreeMap<>();
        for (Score s : scores) {
            grouped.computeIfAbsent(s.getExamName(), k -> new java.util.ArrayList<>()).add(s);
        }

        req.setAttribute("student", student);
        req.setAttribute("groupedScores", grouped);
        req.getRequestDispatcher("/student/myScore.jsp").forward(req, resp);
    }
}
