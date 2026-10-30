package com.grade.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩实体
 */
public class Score {
    private Long id;
    private Long studentId;
    private Long courseId;
    private String examName;
    private BigDecimal score;
    private LocalDateTime createTime;
    // 关联字段
    private String studentName;
    private String studentNo;
    private String courseName;
    private String className;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
