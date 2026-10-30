package com.grade.service;

import com.grade.dao.StudentDao;
import com.grade.entity.Student;
import com.grade.util.PageUtil;

import java.util.List;

/**
 * 学生服务
 */
public class StudentService {

    private final StudentDao studentDao = new StudentDao();

    public PageUtil getPage(int pageNum, int pageSize, Long classId) {
        int total = studentDao.count(classId);
        return new PageUtil(pageNum, pageSize, total);
    }

    public List<Student> findByPage(PageUtil page, Long classId) {
        return studentDao.findByPage(page, classId);
    }

    public Student findById(Long id) {
        return studentDao.findById(id);
    }

    public Student findByUserId(Long userId) {
        return studentDao.findByUserId(userId);
    }

    public void save(Student s) {
        if (s.getId() == null) {
            studentDao.insert(s);
        } else {
            studentDao.update(s);
        }
    }

    public void delete(Long id) {
        studentDao.delete(id);
    }
}
