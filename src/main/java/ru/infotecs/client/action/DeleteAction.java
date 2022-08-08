package ru.infotecs.client.action;

import ru.infotecs.client.input.Input;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.Store;

public class DeleteAction implements StudentAction {

    private final Output out;

    public DeleteAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Delete student";
    }

    @Override
    public boolean execute(Input input, Store store) {
        out.println("=== Delete student ===");
        Long id = Long.parseLong(input.askStr("Enter id: "));
        if (store.delete(id)) {
            out.println("Item is successfully deleted!");
        } else {
            out.println("Wrong id!");
        }
        return true;
    }
}
