package com.demo.stevejobsclassmanaging.model;

public class Course {
    private String name;
    private String status;
    private String year;

    public Course() {
    }

    public Course(String name, String status, String year) {
        this.name = name;
        this.status = status;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
