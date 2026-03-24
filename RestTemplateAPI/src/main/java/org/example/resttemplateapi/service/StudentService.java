package org.example.resttemplateapi.service;

import org.example.resttemplateapi.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class StudentService {
    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Student> getAllStudents() {
        var url = "https://realtimedb-1d0ee-default-rtdb.asia-southeast1.firebasedatabase.app/students.json";
        var students = restTemplate.getForObject(url, Map.class);
        return students != null ? students : new HashMap<>();
    }

    public Student getStudentByKey(String key) {
        var url = "https://realtimedb-1d0ee-default-rtdb.asia-southeast1.firebasedatabase.app/students/" + key + ".json";
        return restTemplate.getForObject(url, Student.class);
    }

    public String createStudents(Student student) {
        var url = "https://realtimedb-1d0ee-default-rtdb.asia-southeast1.firebasedatabase.app/students.json";
        var response = restTemplate.postForObject(url, student, Map.class);
        if (response != null && response.containsKey("name")) {
            return response.get("name").toString();
        }
        return null;
    }

    public void updateStudent(Student student, String key) {
        var url = "https://realtimedb-1d0ee-default-rtdb.asia-southeast1.firebasedatabase.app/students/" + key + ".json";
        restTemplate.put(url, student);
    }

    public void deleteStudent(String key) {
        var url = "https://realtimedb-1d0ee-default-rtdb.asia-southeast1.firebasedatabase.app/students/" + key + ".json";
        restTemplate.delete(url);
    }
}
