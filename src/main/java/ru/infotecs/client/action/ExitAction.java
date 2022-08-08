package ru.infotecs.client.action;

import ru.infotecs.client.input.Input;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.Store;

public class ExitAction implements StudentAction {

    private final Output out;

    public ExitAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, Store store) {
        out.println("=== Exit ===");
        return false;
    }
}
