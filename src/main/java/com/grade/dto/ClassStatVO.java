package com.grade.dto;

import java.math.BigDecimal;

/**
 * 班级/课程统计结果 VO
 */
public class ClassStatVO {
    private String courseName;
    private String examName;
    private String className;
    private int totalStudents;      // 总人数
    private BigDecimal avgScore;    // 平均分
    private BigDecimal maxScore;    // 最高分
    private BigDecimal minScore;    // 最低分
    private double passRate;        // 及格率（百分比，如 85.5 表示 85.5%）
    private double excellentRate;   // 优秀率（>=90分）
    private BigDecimal stdDev;      // 标准差

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }
    public BigDecimal getAvgScore() { return avgScore; }
    public void setAvgScore(BigDecimal avgScore) { this.avgScore = avgScore; }
    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    public BigDecimal getMinScore() { return minScore; }
    public void setMinScore(BigDecimal minScore) { this.minScore = minScore; }
    public double getPassRate() { return passRate; }
    public void setPassRate(double passRate) { this.passRate = passRate; }
    public double getExcellentRate() { return excellentRate; }
    public void setExcellentRate(double excellentRate) { this.excellentRate = excellentRate; }
    public BigDecimal getStdDev() { return stdDev; }
    public void setStdDev(BigDecimal stdDev) { this.stdDev = stdDev; }
}
