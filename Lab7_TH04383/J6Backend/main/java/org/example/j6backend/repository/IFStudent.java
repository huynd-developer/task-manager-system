package org.example.j6backend.repository;

import org.example.j6backend.entity.Student;

import java.util.Collection;

public interface IFStudent {
    Collection<Student> findAll();
    Student findById(String id);
    Student create(Student student);
    Student update(Student student);
    void delete(String id);

}
