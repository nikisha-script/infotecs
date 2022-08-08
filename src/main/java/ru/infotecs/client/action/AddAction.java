package ru.infotecs.client.action;


import ru.infotecs.client.input.Input;
import ru.infotecs.client.model.Student;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.Store;

public class AddAction implements StudentAction {

    private final Output out;

    public AddAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Add a new Student";
    }

    @Override
    public boolean execute(Input input, Store store) {
        out.println("=== Add a new Student ===");
        String name = input.askStr("Enter name: ");
        Student student = new Student(name);
        store.add(student);
        out.println("Item successfully added!");
        return true;
    }
}
