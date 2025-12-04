package org.example.j6backend.controller;

import org.example.j6backend.entity.Student;
import org.example.j6backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class StudentRestApi {
    @Autowired
    StudentService studentService;
    @GetMapping("/students")
    public Collection<Student> getAllStudents() {
        return studentService.findAll();
    }
    @GetMapping("/students/{id}")
    public Student findById(@PathVariable String id) {
        return studentService.findById(id);
    }
    @PostMapping("/students")
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }
    @PutMapping("students/{id}")
    public Student update(@RequestBody Student student, @PathVariable String id) {
        return studentService.update(student);
    }
    @DeleteMapping
    public void deleteById(@PathVariable String id) {
        studentService.delete(id);
    }
}
