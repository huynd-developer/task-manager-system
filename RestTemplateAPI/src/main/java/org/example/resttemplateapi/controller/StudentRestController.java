package org.example.resttemplateapi.controller;

import org.example.resttemplateapi.entity.Student;
import org.example.resttemplateapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
@RestController
public class StudentRestController {
    @Autowired
    StudentService studentService;
    Map<String, Student> StudentMap = new HashMap<>();

    // Lấy tất ca sinh viên
    @GetMapping("/api/students")
    public ResponseEntity<Map<String, Student>> getAllStudents() {
        Map<String, Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Lấy sinh viên theo key
    @GetMapping("/api/students/{key}")
    public ResponseEntity<Student> getStudent(@PathVariable String key) {
        Student student = studentService.getStudentByKey(key);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    // Thêm sinh viên
    @PostMapping("/api/students")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        String key = studentService.createStudents(student);
        if (key != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Create student with key: " + key);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Create student failed");
        }
    }

    // Update sinh viên theo key
    @PutMapping("/api/update/{key}")
    public ResponseEntity<String> updateStudent(@PathVariable String key, @RequestBody Student student) {
        try {
            studentService.updateStudent(student, key); // Xem put bên services student vs key ai tạo trc thì lấy cái đó lên trc
            return ResponseEntity.ok("Update student with key: " + key);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update student failed" + e.getMessage());
        }
    }

    // Delete sinh viên theo key
    @DeleteMapping("/api/delete/{key}")
    public ResponseEntity<String> deleteStudent(@PathVariable String key) {
        try {
            studentService.deleteStudent(key);
            return ResponseEntity.ok("Delete student with key: " + key);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete student failed" + e.getMessage());
        }
    }
}
