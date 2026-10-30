package com.grade.dao;

import com.grade.dto.ClassStatVO;
import com.grade.dto.CriticalStudentVO;
import com.grade.dto.TrendPointVO;
import com.grade.util.DBUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计 DAO（核心统计模块）
 */
public class StatDao {

    /**
     * 班级/课程统计：平均分、最高分、最低分、及格率、优秀率、标准差
     */
    public ClassStatVO getClassStat(Long courseId, Long classId, String examName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 使用聚合函数 + CASE WHEN 计算及格率、优秀率
            // STDDEV_POP 计算总体标准差
            String sql =
                "SELECT " +
                "  c.course_name, " +
                "  ci.class_name, " +
                "  sc.exam_name, " +
                "  COUNT(*) AS total, " +
                "  ROUND(AVG(sc.score), 2) AS avg_score, " +
                "  MAX(sc.score) AS max_score, " +
                "  MIN(sc.score) AS min_score, " +
                "  ROUND(SUM(CASE WHEN sc.score >= 60 THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS pass_rate, " +
                "  ROUND(SUM(CASE WHEN sc.score >= 90 THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS excellent_rate, " +
                "  ROUND(STDDEV_POP(sc.score), 2) AS std_dev " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "JOIN class_info ci ON s.class_id = ci.id " +
                "WHERE sc.course_id = ? AND s.class_id = ? AND sc.exam_name = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, courseId);
            ps.setLong(2, classId);
            ps.setString(3, examName);
            rs = ps.executeQuery();
            if (rs.next()) {
                ClassStatVO vo = new ClassStatVO();
                vo.setCourseName(rs.getString("course_name"));
                vo.setClassName(rs.getString("class_name"));
                vo.setExamName(rs.getString("exam_name"));
                vo.setTotalStudents(rs.getInt("total"));
                vo.setAvgScore(rs.getBigDecimal("avg_score"));
                vo.setMaxScore(rs.getBigDecimal("max_score"));
                vo.setMinScore(rs.getBigDecimal("min_score"));
                vo.setPassRate(rs.getDouble("pass_rate"));
                vo.setExcellentRate(rs.getDouble("excellent_rate"));
                vo.setStdDev(rs.getBigDecimal("std_dev"));
                return vo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    /**
     * 临界生查询：分数在 55-59 之间的学生（可能及格但需要重点关注）
     */
    public List<CriticalStudentVO> findCriticalStudents(Long courseId, Long classId, String examName) {
        List<CriticalStudentVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql =
                "SELECT s.id AS student_id, s.student_no, s.name AS student_name, " +
                "  ci.class_name, c.course_name, sc.exam_name, sc.score " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "JOIN class_info ci ON s.class_id = ci.id " +
                "WHERE sc.course_id = ? AND s.class_id = ? AND sc.exam_name = ? " +
                "  AND sc.score BETWEEN 55 AND 59 " +
                "ORDER BY sc.score DESC";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, courseId);
            ps.setLong(2, classId);
            ps.setString(3, examName);
            rs = ps.executeQuery();
            while (rs.next()) {
                CriticalStudentVO vo = new CriticalStudentVO();
                vo.setStudentId(rs.getLong("student_id"));
                vo.setStudentNo(rs.getString("student_no"));
                vo.setStudentName(rs.getString("student_name"));
                vo.setClassName(rs.getString("class_name"));
                vo.setCourseName(rs.getString("course_name"));
                vo.setExamName(rs.getString("exam_name"));
                vo.setScore(rs.getBigDecimal("score"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 学生个人成绩趋势（历次考试 + 班级平均分 + 排名）
     */
    public List<TrendPointVO> getStudentTrend(Long studentId, Long courseId) {
        List<TrendPointVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 子查询计算班级平均分，外层计算该生排名
            String sql =
                "SELECT sc.exam_name, sc.score, " +
                "  (SELECT ROUND(AVG(s2.score), 2) FROM score s2 " +
                "     JOIN student st2 ON s2.student_id = st2.id " +
                "     WHERE s2.course_id = sc.course_id AND s2.exam_name = sc.exam_name " +
                "       AND st2.class_id = (SELECT class_id FROM student WHERE id = sc.student_id) " +
                "  ) AS class_avg, " +
                "  (SELECT COUNT(*) + 1 FROM score s3 " +
                "     JOIN student st3 ON s3.student_id = st3.id " +
                "     WHERE s3.course_id = sc.course_id AND s3.exam_name = sc.exam_name " +
                "       AND st3.class_id = (SELECT class_id FROM student WHERE id = sc.student_id) " +
                "       AND s3.score > sc.score " +
                "  ) AS rank " +
                "FROM score sc " +
                "WHERE sc.student_id = ? AND sc.course_id = ? " +
                "ORDER BY sc.create_time";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {
                TrendPointVO vo = new TrendPointVO();
                vo.setExamName(rs.getString("exam_name"));
                vo.setScore(rs.getBigDecimal("score"));
                vo.setClassAvg(rs.getBigDecimal("class_avg"));
                vo.setRank(rs.getInt("rank"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 学生总成绩分析（多门课的总分、平均分、班级排名）
     */
    public BigDecimal getStudentTotalAvg(Long studentId, String examName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT ROUND(AVG(score), 2) FROM score WHERE student_id = ? AND exam_name = ?");
            ps.setLong(1, studentId);
            ps.setString(2, examName);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
}
