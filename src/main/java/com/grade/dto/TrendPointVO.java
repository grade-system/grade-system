package com.grade.dto;

import java.math.BigDecimal;

/**
 * 成绩趋势点 VO（用于折线图）
 */
public class TrendPointVO {
    private String examName;      // 考试名称
    private BigDecimal score;     // 分数
    private BigDecimal classAvg;  // 班级平均分（对比用）
    private Integer rank;         // 班级排名

    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    public BigDecimal getClassAvg() { return classAvg; }
    public void setClassAvg(BigDecimal classAvg) { this.classAvg = classAvg; }
    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }
}
