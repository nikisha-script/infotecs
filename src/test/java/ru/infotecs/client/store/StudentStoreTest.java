package ru.infotecs.client.store;

import org.testng.annotations.Test;
import ru.infotecs.client.model.Student;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class StudentStoreTest {

    @Test
    public void whenAddStudent() {
        StudentStore store = new StudentStore();
        Student student = new Student(1L, "Student");
        store.add(student);
        Student expected = store.findById(student.getId());
        assertEquals(expected.getName(), student.getName());
    }

    @Test
    public void whenFindAll() {
        StudentStore store = new StudentStore();
        Student first = new Student(1L, "Student1");
        Student second = new Student(2L, "Student2");
        store.add(first);
        store.add(second);
        List<Student> expected = Arrays.asList(first, second);
        List<Student> result = store.findAll();
        assertEquals(expected, result);
    }

    @Test
    public void whenFindByName() {
        StudentStore store = new StudentStore();
        Student first = new Student(1L, "Danil");
        Student second = new Student(2L, "Petr");
        Student third = new Student(3L, "Petr");
        store.add(first);
        store.add(second);
        store.add(third);
        List<Student> expected = Arrays.asList(second, third);
        List<Student> result = store.findByName("Petr");
        assertEquals(expected, result);
    }

    @Test
    public void whenFindById() {
        StudentStore store = new StudentStore();
        Student first = new Student(1L, "Danil");
        Student second = new Student(2L, "Petr");
        Student third = new Student(3L, "Ivan");
        store.add(first);
        store.add(second);
        store.add(third);
        Student expected = store.findById(2L);
        assertEquals(expected, second);
    }

    @Test
    public void whenFindByIdNotFound() {
        StudentStore store = new StudentStore();
        Student first = new Student(1L, "Danil");
        Student second = new Student(2L, "Petr");
        Student third = new Student(3L, "Ivan");
        store.add(first);
        store.add(second);
        store.add(third);
        Student expected = store.findById(4L);
        assertNull(expected);
    }

    @Test
    public void whenDelete() {
        StudentStore store = new StudentStore();
        Student first = new Student(1L, "Danil");
        store.add(first);
        store.delete(first.getId());
        assertNull(store.findById(first.getId()));
    }

}