package com.grade.dao;

import com.grade.entity.ClassInfo;
import com.grade.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 班级 DAO
 */
public class ClassInfoDao {

    /** 查询所有班级 */
    public List<ClassInfo> findAll() {
        List<ClassInfo> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT id, class_name, grade FROM class_info ORDER BY id");
            rs = ps.executeQuery();
            while (rs.next()) {
                ClassInfo c = new ClassInfo();
                c.setId(rs.getLong("id"));
                c.setClassName(rs.getString("class_name"));
                c.setGrade(rs.getString("grade"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public ClassInfo findById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT id, class_name, grade FROM class_info WHERE id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ClassInfo c = new ClassInfo();
                c.setId(rs.getLong("id"));
                c.setClassName(rs.getString("class_name"));
                c.setGrade(rs.getString("grade"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public void insert(ClassInfo c) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO class_info(class_name, grade) VALUES(?, ?)");
            ps.setString(1, c.getClassName());
            ps.setString(2, c.getGrade());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    public void update(ClassInfo c) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE class_info SET class_name = ?, grade = ? WHERE id = ?");
            ps.setString(1, c.getClassName());
            ps.setString(2, c.getGrade());
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
            ps = conn.prepareStatement("DELETE FROM class_info WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }
}
