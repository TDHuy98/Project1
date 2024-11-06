package com.intern.project1.entities.dto;

public class PeriodTime {
    private final Integer beginMonth;
    private final Integer beginYear;
    private final Integer endMonth;
    private final Integer endYear;

    public PeriodTime(Integer beginMonth, Integer beginYear, Integer endMonth, Integer endYear) {
        this.beginMonth = beginMonth;
        this.beginYear = beginYear;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    public Integer getBeginMonth() {
        return beginMonth;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PeriodTime{");
        sb.append("beginMonth=").append(beginMonth);
        sb.append(", beginYear=").append(beginYear);
        sb.append(", endMonth=").append(endMonth);
        sb.append(", endYear=").append(endYear);
        sb.append('}');
        return sb.toString();
    }
}
