package ru.infotecs.client.action;


import ru.infotecs.client.input.Input;
import ru.infotecs.client.store.Store;

public interface StudentAction {

    String name();

    boolean execute(Input input, Store store);

}
