package ru.infotecs.data;

import ru.infotecs.data.model.User;

import java.util.ArrayList;
import java.util.List;

public final class Store {

    private static final Store INSTANCE = new Store();
    private final List<User> users;

    private Store() {
        this.users = new ArrayList<>();
        users.add(new User("admin", "admin"));
    }

    public static Store getInstance() {
        return INSTANCE;
    }

    public List<User> getUsers() {
        return users;
    }
}
