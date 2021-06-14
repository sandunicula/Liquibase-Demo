package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.DTO.CourseDTO;
import com.example.demo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        logger.info("Retrieved all courses");
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Optional<Course>> getCourse(@PathVariable Long id) {
        logger.info("Retrieved course by id");
        Optional<Course> course = courseRepository.findById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@RequestBody CourseDTO updatedCourse, @PathVariable Long id) {
        logger.info("Updated course with id: {}", id);
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
        logger.info("Added new course with id: {}", newCourse.getId());
        return courseRepository.save(newCourse);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        logger.info("Deleted course with id: {}", id);
        courseRepository.deleteById(id);
    }
}
