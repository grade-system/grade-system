package com.grade.dto;

import java.math.BigDecimal;

/**
 * 临界生 VO（分数在 55-59 分之间，需要重点关注）
 */
public class CriticalStudentVO {
    private Long studentId;
    private String studentNo;
    private String studentName;
    private String className;
    private String courseName;
    private String examName;
    private BigDecimal score;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
}
