package com.example.demo.entity.DTO;

import com.example.demo.entity.Course;

import java.util.Objects;

public class CourseDTO extends Course {

    private Long id;
    private String title;
    private Long credits;
    private String teacherName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return id.equals(courseDTO.id) &&
                Objects.equals(title, courseDTO.title) &&
                Objects.equals(credits, courseDTO.credits) &&
                Objects.equals(teacherName, courseDTO.teacherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, credits, teacherName);
    }
}
