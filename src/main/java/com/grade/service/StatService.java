package com.grade.service;

import com.grade.dao.StatDao;
import com.grade.dto.ClassStatVO;
import com.grade.dto.CriticalStudentVO;
import com.grade.dto.TrendPointVO;

import java.util.List;

/**
 * 统计服务（核心差异化模块）
 */
public class StatService {

    private final StatDao statDao = new StatDao();

    /**
     * 班级/课程统计：平均分、最高分、最低分、及格率、优秀率、标准差
     */
    public ClassStatVO getClassStat(Long courseId, Long classId, String examName) {
        return statDao.getClassStat(courseId, classId, examName);
    }

    /**
     * 临界生查询（55-59分）
     */
    public List<CriticalStudentVO> findCriticalStudents(Long courseId, Long classId, String examName) {
        return statDao.findCriticalStudents(courseId, classId, examName);
    }

    /**
     * 学生个人成绩趋势
     */
    public List<TrendPointVO> getStudentTrend(Long studentId, Long courseId) {
        return statDao.getStudentTrend(studentId, courseId);
    }
}
