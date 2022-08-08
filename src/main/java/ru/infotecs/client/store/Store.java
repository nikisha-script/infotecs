package ru.infotecs.client.store;


import ru.infotecs.client.model.Student;

import java.util.List;

public interface Store {

    Student add(Student student);

    List<Student> findByName(String key);

    Student findById(Long id);

    boolean delete(Long id);

    List<Student> findAll();

}
