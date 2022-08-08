package ru.infotecs.client.store;

import ru.infotecs.client.model.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentStore implements Store {

    private final List<Student> students = new ArrayList<>();
    private Long id = 1L;

    @Override
    public Student add(Student student) {
        if (student.getId() == null) {
            student.setId(++id);
        }
        id = student.getId();
        students.add(student);
        Collections.sort(students);
        return student;
    }

    @Override
    public List<Student> findByName(String key) {
        return students.stream()
                .filter(student -> key.equals(student.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Student findById(Long id) {
        int index = indexOf(id);
        return index != -1 ? students.get(index) : null;
    }

    @Override
    public boolean delete(Long id) {
        int index = indexOf(id);
        if (index == -1) {
            return false;
        }
        students.remove(index);
        return true;
    }

    @Override
    public List<Student> findAll() {
        return students;
    }

    private int indexOf(Long id) {
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            if (Objects.equals(students.get(i).getId(), id)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
