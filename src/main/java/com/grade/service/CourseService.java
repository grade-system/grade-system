package com.grade.service;

import com.grade.dao.CourseDao;
import com.grade.entity.Course;

import java.util.List;

/**
 * 课程服务
 */
public class CourseService {

    private final CourseDao courseDao = new CourseDao();

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public List<Course> findByTeacherId(Long teacherId) {
        return courseDao.findByTeacherId(teacherId);
    }

    public Course findById(Long id) {
        return courseDao.findById(id);
    }

    public void save(Course c) {
        if (c.getId() == null) {
            courseDao.insert(c);
        } else {
            courseDao.update(c);
        }
    }

    public void delete(Long id) {
        courseDao.delete(id);
    }
}
