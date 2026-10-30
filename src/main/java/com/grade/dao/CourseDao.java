package com.grade.dao;

import com.grade.entity.Course;
import com.grade.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程 DAO
 */
public class CourseDao {

    /** 查询所有课程（带教师姓名） */
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.id, c.course_name, c.teacher_id, u.real_name AS teacher_name " +
                         "FROM course c LEFT JOIN sys_user u ON c.teacher_id = u.id ORDER BY c.id";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setCourseName(rs.getString("course_name"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setTeacherName(rs.getString("teacher_name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    /** 根据教师ID查询其授课课程 */
    public List<Course> findByTeacherId(Long teacherId) {
        List<Course> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.id, c.course_name, c.teacher_id, u.real_name AS teacher_name " +
                         "FROM course c LEFT JOIN sys_user u ON c.teacher_id = u.id WHERE c.teacher_id = ? ORDER BY c.id";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, teacherId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setCourseName(rs.getString("course_name"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setTeacherName(rs.getString("teacher_name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public Course findById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT id, course_name, teacher_id FROM course WHERE id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setCourseName(rs.getString("course_name"));
                c.setTeacherId(rs.getLong("teacher_id"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public void insert(Course c) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO course(course_name, teacher_id) VALUES(?, ?)");
            ps.setString(1, c.getCourseName());
            ps.setLong(2, c.getTeacherId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    public void update(Course c) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE course SET course_name = ?, teacher_id = ? WHERE id = ?");
            ps.setString(1, c.getCourseName());
            ps.setLong(2, c.getTeacherId());
            ps.setLong(3, c.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    public void delete(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("DELETE FROM course WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }
}
