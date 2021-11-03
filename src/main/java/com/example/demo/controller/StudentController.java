package com.example.demo.controller;

import com.example.demo.entity.DTO.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.ReportService;
import net.sf.jasperreports.engine.JRException;
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

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    StudentRepository studentRepository;
    ReportService service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public StudentController(StudentRepository studentRepository,
                             ReportService service) {
        this.studentRepository = studentRepository;
        this.service = service;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents() {
        logger.info("Retrieved all students");
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("students/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable Long id) {
        logger.info("Retrieved student with id {} ", id);
        Optional<Student> student = studentRepository.findById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@RequestBody StudentDTO updatedStudent, @PathVariable Long id) {
        logger.info("Updated student with id {} ", id);
        Optional<Student> student = studentRepository.findById(id);
        return student.map(s -> {
            s.setFirstName(updatedStudent.getFirstName());
            s.setLastName(updatedStudent.getLastName());
            s.setEmail(updatedStudent.getEmail());
            s.setPhone(updatedStudent.getPhone());
            return studentRepository.save(s);
        }).orElseGet(() -> {
            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        });
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student newStudent) {
        logger.info("Added student with id {} ", newStudent.getId());
        return studentRepository.save(newStudent);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        logger.info("Deleted student with id {} ", id);
        studentRepository.deleteById(id);
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }
}
