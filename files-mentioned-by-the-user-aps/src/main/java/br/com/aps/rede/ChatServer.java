package br.com.aps.rede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int DEFAULT_PORT = 5050;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final int port;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new ChatServer(port).start();
    }

    public void start() {
        System.out.println("Servidor iniciado na porta " + port);
        System.out.println("Aguardando equipes de inspecao e Secretaria...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException exception) {
            System.out.println("Erro no servidor: " + exception.getMessage());
        }
    }

    private void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.send(message);
                }
            }
        }
    }

    private String withTime(String message) {
        return "[" + LocalTime.now().format(TIME_FORMAT) + "] " + message;
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter output;
        private String name = "Usuario";
        private boolean announced;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)
            ) {
                output = writer;
                send("Conectado ao servidor da APS.");
                send("Informe mensagens sobre as industrias monitoradas. Digite /sair para encerrar.");

                String informedName = input.readLine();
                if (informedName == null) {
                    return;
                }

                if (informedName.trim().isEmpty()) {
                    name = "Usuario";
                } else {
                    name = informedName.trim();
                }

                String enterMessage = withTime(name + " entrou no canal.");
                System.out.println(enterMessage);
                broadcast(enterMessage, this);
                announced = true;

                String line;
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("/sair")) {
                        break;
                    }

                    String message = withTime(name + ": " + line);
                    System.out.println(message);
                    broadcast(message, this);
                }
            } catch (IOException exception) {
                System.out.println("Cliente desconectado: " + exception.getMessage());
            } finally {
                clients.remove(this);
                if (announced) {
                    String exitMessage = withTime(name + " saiu do canal.");
                    System.out.println(exitMessage);
                    broadcast(exitMessage, this);
                }
                closeSocket();
            }
        }

        void send(String message) {
            if (output != null) {
                output.println(message);
            }
        }

        private void closeSocket() {
            try {
                socket.close();
            } catch (IOException ignored) {
                // O socket ja pode estar fechado.
            }
        }
    }
}
