package com.example;

import java.io.IOException;
import java.io.*;
import java.net.*;

/**
 * @author WangYunwei [2024-07-04]
 */
public class TcpServerTest {

    private int port = 8080;

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // 等待客户端连接
                new ClientHandler(clientSocket).start(); // 处理客户端连接（在新的线程中）
            }
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Server received: " + inputLine);
                    out.println("Echo: " + inputLine); // 回显到客户端
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args){
        TcpServerTest server = new TcpServerTest();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
