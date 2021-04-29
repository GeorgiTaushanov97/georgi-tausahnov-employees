package com.company;

public class Pair {
    private String firstEmpId;
    private String secondEmpId;
    private long daysOfWorkTogether;

    public Pair(String firstEmp, String secondEmp, long hours) {
        this.firstEmpId = firstEmp;
        this.secondEmpId = secondEmp;
        this.daysOfWorkTogether = hours;
    }

    public String getFirstEmpId() {
        return firstEmpId;
    }

    public String getSecondEmpId() {
        return secondEmpId;
    }

    public long getDaysOfWorkTogether() {
        return daysOfWorkTogether;
    }

    public void setDaysOfWorkTogether(long days) {
        this.daysOfWorkTogether = days;
    }
}
