package com.grade.dao;

import com.grade.entity.Student;
import com.grade.util.DBUtil;
import com.grade.util.PageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生 DAO
 */
public class StudentDao {

    /** 分页查询学生（带班级信息） */
    public List<Student> findByPage(PageUtil page, Long classId) {
        List<Student> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder(
                "SELECT s.id, s.student_no, s.name, s.class_id, c.class_name, c.grade " +
                "FROM student s LEFT JOIN class_info c ON s.class_id = c.id WHERE 1=1 ");
            if (classId != null) {
                sql.append("AND s.class_id = ? ");
            }
            sql.append("ORDER BY s.student_no LIMIT ?, ?");
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (classId != null) {
                ps.setLong(idx++, classId);
            }
            ps.setInt(idx++, page.getOffset());
            ps.setInt(idx, page.getPageSize());
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

    /** 统计总数 */
    public int count(Long classId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM student WHERE 1=1";
            if (classId != null) sql += " AND class_id = ?";
            ps = conn.prepareStatement(sql);
            if (classId != null) ps.setLong(1, classId);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    /** 根据用户ID查询学生 */
    public Student findByUserId(Long userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT s.id, s.student_no, s.name, s.class_id, c.class_name, c.grade " +
                "FROM student s LEFT JOIN class_info c ON s.class_id = c.id WHERE s.user_id = ?");
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public Student findById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT s.id, s.student_no, s.name, s.class_id, c.class_name, c.grade " +
                "FROM student s LEFT JOIN class_info c ON s.class_id = c.id WHERE s.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public void insert(Student s) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO student(student_no, name, class_id, user_id) VALUES(?, ?, ?, ?)");
            ps.setString(1, s.getStudentNo());
            ps.setString(2, s.getName());
            ps.setLong(3, s.getClassId());
            ps.setObject(4, s.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    public void update(Student s) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE student SET student_no = ?, name = ?, class_id = ? WHERE id = ?");
            ps.setString(1, s.getStudentNo());
            ps.setString(2, s.getName());
            ps.setLong(3, s.getClassId());
            ps.setLong(4, s.getId());
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
            ps = conn.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    private Student mapRow(ResultSet rs) throws Exception {
        Student s = new Student();
        s.setId(rs.getLong("id"));
        s.setStudentNo(rs.getString("student_no"));
        s.setName(rs.getString("name"));
        s.setClassId(rs.getLong("class_id"));
        s.setClassName(rs.getString("class_name"));
        s.setGrade(rs.getString("grade"));
        return s;
    }
}
