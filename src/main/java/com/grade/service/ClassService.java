package com.grade.service;

import com.grade.dao.ClassInfoDao;
import com.grade.entity.ClassInfo;

import java.util.List;

/**
 * 班级服务
 */
public class ClassService {

    private final ClassInfoDao classDao = new ClassInfoDao();

    public List<ClassInfo> findAll() {
        return classDao.findAll();
    }

    public ClassInfo findById(Long id) {
        return classDao.findById(id);
    }

    public void save(ClassInfo c) {
        if (c.getId() == null) {
            classDao.insert(c);
        } else {
            classDao.update(c);
        }
    }

    public void delete(Long id) {
        classDao.delete(id);
    }
}
