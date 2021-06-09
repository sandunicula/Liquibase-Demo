package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.DTO.CourseDTO;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courses  = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Optional<Course>> getCourse(@PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@RequestBody CourseDTO updatedCourse, @PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(c -> {
            c.setTitle(updatedCourse.getTitle());
            c.setCredits(updatedCourse.getCredits());
            c.setTeacherName(updatedCourse.getTeacherName());
            return courseRepository.save(c);
        }).orElseGet(() -> {
            updatedCourse.setId(id);
            return courseRepository.save(updatedCourse);
        });
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course newCourse) {
        return courseRepository.save(newCourse);
    }
}
