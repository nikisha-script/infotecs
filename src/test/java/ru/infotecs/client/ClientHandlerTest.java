package ru.infotecs.client;

import org.testng.annotations.Test;
import ru.infotecs.client.action.*;
import ru.infotecs.client.input.Input;
import ru.infotecs.client.input.StubInput;
import ru.infotecs.client.model.Student;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.output.StubOutput;
import ru.infotecs.client.store.StudentStore;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class ClientHandlerTest {


    @Test
    public void whenAddStudentAction() {
        Output output = new StubOutput();
        StudentStore store = new StudentStore();
        Student first = store.add(new Student(1L, "Danil"));
        Input input = new StubInput(
                new String[] {"0", first.getName(), "1"}
        );
        List<StudentAction> actions = new ArrayList<>();
        actions.add(new AddAction(output));
        actions.add(new ExitAction(output));
        new ClientHandler(output).initAction(input, store, actions);
        String builder =
                "=== Add a new Student ===" + System.lineSeparator()
                        + "Item successfully added!" + System.lineSeparator()
                        + "=== Exit ===" + System.lineSeparator();
        assertSame(output.toString(), builder);
    }

    @Test
    public void whenFindByIdAction() {
        Output output = new StubOutput();
        StudentStore store = new StudentStore();
        Student first = store.add(new Student(1L, "Danil"));
        Input input = new StubInput(
                new String[] {"0", String.valueOf(first.getId()), "1"}
        );
        List<StudentAction> actions = new ArrayList<>();
        actions.add(new FindByIdAction(output));
        actions.add(new ExitAction(output));
        new ClientHandler(output).initAction(input, store, actions);
        String builder =
                "=== Find Student by Id ===" + System.lineSeparator()
                        + first + System.lineSeparator()
                        + "=== Exit ===" + System.lineSeparator();
        assertSame(output.toString(), builder);
    }

    @Test
    public void whenFindByNameAction() {
        Output output = new StubOutput();
        StudentStore store = new StudentStore();
        Student first = store.add(new Student(1L, "Danil"));
        Student second = store.add(new Student(2L, "Danil"));
        Input input = new StubInput(
                new String[] {"0", String.valueOf(first.getName()), "1"}
        );
        List<StudentAction> actions = new ArrayList<>();
        actions.add(new FindByNameAction(output));
        actions.add(new ExitAction(output));
        new ClientHandler(output).initAction(input, store, actions);
        String builder =
                "=== Find students by name ===" + System.lineSeparator()
                        + first + System.lineSeparator()
                        + second + System.lineSeparator()
                        + "=== Exit ===" + System.lineSeparator();
        assertSame(output.toString(), builder);
    }

    @Test
    public void whenDeleteStudentAction() {
        Output output = new StubOutput();
        StudentStore store = new StudentStore();
        Student first = store.add(new Student(1L, "Danil"));
        Input input = new StubInput(
                new String[] {"0", String.valueOf(first.getId()), "1"}
        );
        List<StudentAction> actions = new ArrayList<>();
        actions.add(new DeleteAction(output));
        actions.add(new ExitAction(output));
        new ClientHandler(output).initAction(input, store, actions);
        String builder =
                "=== Delete student ===" + System.lineSeparator()
                        + "Item is successfully deleted!" + System.lineSeparator()
                        + "=== Exit ===" + System.lineSeparator();
        assertSame(output.toString(), builder);
    }

}