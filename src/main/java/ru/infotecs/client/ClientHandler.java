package ru.infotecs.client;


import com.google.gson.Gson;
import ru.infotecs.client.action.*;
import ru.infotecs.client.input.ConsoleInput;
import ru.infotecs.client.input.Input;
import ru.infotecs.client.input.ValidateInput;
import ru.infotecs.client.model.Student;
import ru.infotecs.client.output.ConsoleOutput;
import ru.infotecs.client.output.Output;
import ru.infotecs.client.store.StudentStore;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class ClientHandler {

    private int port;
    private String ip;
    private byte[] byteArray = new byte[1024];
    private String login;
    private String password;
    private Gson gson;
    private Output output;
    private Input validate;
    private List<StudentAction> actions;
    private StudentAction action;

    public ClientHandler() throws IOException {
        init();
    }

    public ClientHandler(Output output) {
        this.output = output;
    }

    public void init() throws IOException {
        out.print("Port: ");
        port = Integer.parseInt(getString());
        out.print("IP: ");
        ip = getString();
        try (Socket socket = new Socket(ip, port);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             InputStream input = socket.getInputStream()) {
            out.print("login: ");
            login = getString();
            out.print("password: ");
            password = getString();
            writer.write(login + " " + password);
            writer.flush();

            File file = new File("D:\\java\\small-project\\infotecs\\src\\main\\java\\ru\\infotecs\\client\\received\\data.json");
            try (FileOutputStream out = new FileOutputStream(file)) {
                int bytesRead = input.read(byteArray, 0, byteArray.length);
                int current = bytesRead;
                do {
                    bytesRead = input.read(byteArray, current, (byteArray.length - current));
                    if (bytesRead >= 0) {
                        current += bytesRead;
                    }
                } while (bytesRead > -1);
                out.write(byteArray, 0, current);
                parsingJsonFile(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsingJsonFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str.replaceAll(" ", ""));
            }
            if ("error".contentEquals(sb)) {
                out.println("you entered the wrong username or password");
            } else {
                gson = new Gson();
                Student[] studentsArray = gson.fromJson(sb.toString(), Student[].class);
                showActions(studentsArray);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showActions(Student[] studentsArray) {
        output = new ConsoleOutput();
        validate = new ValidateInput(output, new ConsoleInput());
        actions = new ArrayList<>();
        actions.add(new AddAction(output));
        actions.add(new DeleteAction(output));
        actions.add(new FindByIdAction(output));
        actions.add(new FindByNameAction(output));
        actions.add(new FindAllAction(output));
        actions.add(new ExitAction(output));
        StudentStore store = new StudentStore();
        Arrays.stream(studentsArray).forEach(store::add);

        initAction(validate, store, actions);
    }

    public void initAction(Input validate, StudentStore store, List<StudentAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = validate.askInt("Enter select: ");
            if (select < actions.size()) {
                action = actions.get(select);
                run = action.execute(validate, store);
            } else {
                out.println("You chose the wrong action! Repeat one more time");
            }
        }
    }

    private void showMenu(List<StudentAction> actions) {
        out.println("Menu.");
        for (int i = 0; i < actions.size(); i++) {
            out.println(i + ". " + actions.get(i).name());
        }
    }

    private String getString() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

}
