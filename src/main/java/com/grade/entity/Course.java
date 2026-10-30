package com.grade.entity;

import java.time.LocalDateTime;

/**
 * 课程实体
 */
public class Course {
    private Long id;
    private String courseName;
    private Long teacherId;
    private LocalDateTime createTime;
    // 关联字段（查询时填充）
    private String teacherName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
}
