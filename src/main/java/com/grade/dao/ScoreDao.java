package com.grade.dao;

import com.grade.entity.Score;
import com.grade.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 成绩 DAO
 */
public class ScoreDao {

    /**
     * 批量录入成绩（使用 ON DUPLICATE KEY UPDATE 实现重复录入时覆盖）
     */
    public void batchSave(List<Score> scores) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO score(student_id, course_id, exam_name, score) " +
                         "VALUES(?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE score = VALUES(score)";
            ps = conn.prepareStatement(sql);
            for (Score s : scores) {
                ps.setLong(1, s.getStudentId());
                ps.setLong(2, s.getCourseId());
                ps.setString(3, s.getExamName());
                ps.setBigDecimal(4, s.getScore());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    /**
     * 按条件查询成绩列表（教师端：按课程/班级/考试查询）
     */
    public List<Score> findScores(Long courseId, Long classId, String examName) {
        List<Score> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder(
                "SELECT sc.id, sc.student_id, sc.course_id, sc.exam_name, sc.score, " +
                "s.name AS student_name, s.student_no, c.course_name, ci.class_name " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "JOIN class_info ci ON s.class_id = ci.id " +
                "WHERE 1=1 ");
            List<Object> params = new ArrayList<>();
            if (courseId != null) {
                sql.append("AND sc.course_id = ? ");
                params.add(courseId);
            }
            if (classId != null) {
                sql.append("AND s.class_id = ? ");
                params.add(classId);
            }
            if (examName != null && !examName.trim().isEmpty()) {
                sql.append("AND sc.exam_name = ? ");
                params.add(examName);
            }
            sql.append("ORDER BY s.student_no");
            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 查询某学生的所有成绩（学生端）
     */
    public List<Score> findByStudentId(Long studentId) {
        List<Score> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT sc.id, sc.student_id, sc.course_id, sc.exam_name, sc.score, " +
                "s.name AS student_name, c.course_name " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "WHERE sc.student_id = ? " +
                "ORDER BY sc.exam_name, c.course_name");
            ps.setLong(1, studentId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 查询某学生某课程的历次考试成绩（趋势图用）
     */
    public List<Score> findTrendByStudentAndCourse(Long studentId, Long courseId) {
        List<Score> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT sc.exam_name, sc.score " +
                "FROM score sc WHERE sc.student_id = ? AND sc.course_id = ? " +
                "ORDER BY sc.create_time");
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Score s = new Score();
                s.setExamName(rs.getString("exam_name"));
                s.setScore(rs.getBigDecimal("score"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 删除成绩
     */
    public void delete(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("DELETE FROM score WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    /**
     * 查询所有不重复的考试名称（下拉框用）
     */
    public List<String> findDistinctExamNames() {
        List<String> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT DISTINCT exam_name FROM score ORDER BY exam_name");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("exam_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    private Score mapRow(ResultSet rs) throws Exception {
        Score s = new Score();
        s.setId(rs.getLong("id"));
        s.setStudentId(rs.getLong("student_id"));
        s.setCourseId(rs.getLong("course_id"));
        s.setExamName(rs.getString("exam_name"));
        s.setScore(rs.getBigDecimal("score"));
        s.setStudentName(rs.getString("student_name"));
        s.setCourseName(rs.getString("course_name"));
        try { s.setStudentNo(rs.getString("student_no")); } catch (Exception ignored) {}
        try { s.setClassName(rs.getString("class_name")); } catch (Exception ignored) {}
        return s;
    }
}
