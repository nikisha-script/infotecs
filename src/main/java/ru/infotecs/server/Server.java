package ru.infotecs.server;

import ru.infotecs.data.Store;
import ru.infotecs.data.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 9000;
    private final int poolThreads = Runtime.getRuntime().availableProcessors();
    private final ExecutorService pool = Executors.newFixedThreadPool(poolThreads);
    private final List<User> users = Store.getInstance().getUsers();
    private final int bufferSize = 1024 * 1024;

    public void init() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("server run!");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to the server!");
                pool.submit(() -> {
                   try (OutputStream out = socket.getOutputStream();
                        InputStream input = socket.getInputStream();
                        FileInputStream  in = new FileInputStream("D:\\java\\small-project\\infotecs\\src\\main\\java\\ru\\infotecs\\server\\json\\students.json")
                   ) {
                       byte[] buff = new byte[1_000_000];
                       int total = input.read(buff);
                       String text = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                       User auth = getAuth(text);
                       if (users.stream().anyMatch(elem -> elem.equals(auth))) {
                           byte[] dataBuffer = new byte[bufferSize];
                           int byteRead;
                           while ((byteRead = in.read(dataBuffer, 0, 1024)) != -1) {
                               out.write(dataBuffer, 0, byteRead);
                               System.out.println("file sent successfully");
                           }
                       } else {
                           out.write("error".getBytes(StandardCharsets.UTF_8));
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getAuth(String text) {
        String[] result = text.split(" ");
        return new User(result[0], result[1]);
    }

    public static void main(String[] args) {
        new Server().init();
    }
}
