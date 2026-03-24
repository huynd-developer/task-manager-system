package org.example.j6backend.service;

import org.example.j6backend.entity.Student;
import org.example.j6backend.repository.IFStudent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService implements IFStudent {
    Map<String, Student> db = new HashMap<>(Map.of(
            "SV1",new Student("SV1","Sinh viên 1",true,9.0),
            "SV2",new Student("SV2","Sinh viên 2",false,7.0),
            "SV3",new Student("SV3","Sinh viên 3",true,6.0),
            "SV4",new Student("SV4","Sinh viên 4",false,5.0),
            "SV5",new Student("SV5","Sinh viên 5",true,4.0)
    ));
    @Override
    public Collection<Student> findAll(){
        return db.values();
    }
    @Override
    public Student findById(String id){
        return db.get(id);
    }
    @Override
    public Student create(Student student){
        return db.put(student.getId(),student);
    }
    @Override
    public Student update(Student student){
        return db.put(student.getId(),student);
    }
    @Override
    public void delete(String id){
        db.remove(id);
    }
}
