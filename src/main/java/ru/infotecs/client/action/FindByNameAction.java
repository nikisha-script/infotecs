package ru.infotecs.client.action;


import ru.infotecs.client.input.Input;
import ru.infotecs.client.model.Student;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.Store;

import java.util.List;

public class FindByNameAction implements StudentAction {

    private final Output out;

    public FindByNameAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find students by name";
    }

    @Override
    public boolean execute(Input input, Store store) {
        out.println("=== Find students by name ===");
        String name = input.askStr("Enter name: ");
        List<Student> items = store.findByName(name);
        for (Student student: items) {
            out.println(student);
        }
        return true;
    }
}
