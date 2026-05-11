package br.com.aps.rede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 5050;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        int port = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_PORT;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter serverOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                Scanner keyboard = new Scanner(System.in, StandardCharsets.UTF_8.name())
        ) {
            System.out.println("Conectado em " + host + ":" + port);
            System.out.print("Digite seu nome ou equipe: ");
            String name = keyboard.nextLine();
            serverOutput.println(name);

            Thread receiver = new Thread(() -> receiveMessages(serverInput));
            receiver.setDaemon(true);
            receiver.start();

            System.out.println("Digite uma mensagem. Use /sair para encerrar.");
            while (true) {
                String message = keyboard.nextLine();
                serverOutput.println(message);

                if (message.equalsIgnoreCase("/sair")) {
                    break;
                }
            }
        } catch (IOException exception) {
            System.out.println("Erro no cliente: " + exception.getMessage());
        }
    }

    private static void receiveMessages(BufferedReader serverInput) {
        try {
            String message;
            while ((message = serverInput.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException exception) {
            System.out.println("Conexao com o servidor encerrada.");
        }
    }
}
