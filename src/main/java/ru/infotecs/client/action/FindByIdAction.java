package ru.infotecs.client.action;

import ru.infotecs.client.input.Input;
import ru.infotecs.client.model.Student;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.Store;

public class FindByIdAction implements StudentAction {

    private final Output out;

    public FindByIdAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find Student by Id";
    }

    @Override
    public boolean execute(Input input, Store store) {
        out.println("=== Find Student by Id ===");
        Long id = Long.parseLong(input.askStr("Enter id: "));
        Student student = store.findById(id);
        if (student != null) {
            out.println(student);
        } else {
            out.println("Wrong id! Not found");
        }
        return true;
    }
}
