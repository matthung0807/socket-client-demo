package com.abc.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class App {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        new SocketClient(HOST, PORT);
    }

    static class SocketClient {

        SocketClient(String host, int port) {
            System.out.println("Socket Client starting...");

            try (
                    Socket socket = new Socket(host, port);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                System.out.println("Socket connected, host=" + host + ", port=" + port);

                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String serverMessage = "";
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(userInput)) {
                        break;
                    }
                    System.out.println("User input: " + userInput);
                    out.println(userInput);
                    serverMessage = in.readLine();
                    System.out.println("Socket Server: " + serverMessage);
                }
                System.out.println("Socket closed.");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
