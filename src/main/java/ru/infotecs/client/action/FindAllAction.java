package ru.infotecs.client.action;

import ru.infotecs.client.input.Input;
import ru.infotecs.client.model.Student;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.Store;
import java.util.List;

public class FindAllAction implements StudentAction {

    private final Output out;

    public FindAllAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find all students";
    }

    @Override
    public boolean execute(Input input, Store store) {
        out.println("=== Find all students ====");
        List<Student> students = store.findAll();
        students.forEach(System.out::println);
        return true;
    }
}
