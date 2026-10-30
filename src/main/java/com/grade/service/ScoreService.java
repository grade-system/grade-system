package com.grade.service;

import com.grade.dao.ScoreDao;
import com.grade.entity.Score;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成绩服务
 */
public class ScoreService {

    private final ScoreDao scoreDao = new ScoreDao();

    /**
     * 批量录入成绩（校验分数 0-100）
     */
    public void batchSave(List<Score> scores) {
        for (Score s : scores) {
            BigDecimal sc = s.getScore();
            if (sc == null || sc.compareTo(BigDecimal.ZERO) < 0 || sc.compareTo(new BigDecimal("100")) > 0) {
                throw new IllegalArgumentException("分数必须在 0-100 之间：" + s.getStudentName());
            }
        }
        scoreDao.batchSave(scores);
    }

    /**
     * 教师端按条件查询成绩
     */
    public List<Score> findScores(Long courseId, Long classId, String examName) {
        return scoreDao.findScores(courseId, classId, examName);
    }

    /**
     * 学生端查询自己的成绩
     */
    public List<Score> findByStudentId(Long studentId) {
        return scoreDao.findByStudentId(studentId);
    }

    /**
     * 查询学生某课程历次成绩（趋势图）
     */
    public List<Score> findTrend(Long studentId, Long courseId) {
        return scoreDao.findTrendByStudentAndCourse(studentId, courseId);
    }

    public List<String> findExamNames() {
        return scoreDao.findDistinctExamNames();
    }

    public void delete(Long id) {
        scoreDao.delete(id);
    }
}
