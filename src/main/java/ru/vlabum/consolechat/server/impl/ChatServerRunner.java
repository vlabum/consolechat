package ru.vlabum.consolechat.server.impl;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.vlabum.consolechat.api.ConnectionService;
import ru.vlabum.consolechat.config.ChatConfig;
import ru.vlabum.consolechat.server.Server;
import ru.vlabum.consolechat.server.model.Connection;
import ru.vlabum.consolechat.server.service.ConnectionServiceBean;
import ru.vlabum.consolechat.server.task.AbstractServerTask;
import ru.vlabum.consolechat.server.task.ServerTaskConnection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Постановка задач в потоки и управления сокетами
 * Тут же описан первичный запуск сервера
 */
@Getter
public class ChatServerRunner implements Server {
    private final ChatConfig config;
    private final ExecutorService executor;
    private final ConnectionService connectionService; // управляет подключениями
    private ServerSocket serverSocket;

    public ChatServerRunner(final ChatConfig config, final ExecutorService executor) {
        this.connectionService = new ConnectionServiceBean(this);
        this.config = config;
        this.executor = executor;
    }

    /**
     * Запуск первой команды на ожидание подключения.
     * Создаем самый первый сокет и запускаем задачу на ожидание подключения в соседнем потоке
     */
    @Override
    @SneakyThrows
    public void run() {
        serverSocket = new ServerSocket(config.getPort());
        AbstractServerTask task = new ServerTaskConnection(this);
        run(task);
    }

    /**
     * Постановка задач в отдельном потоке
     * @param task - задача
     */
    @Override
    public void run(AbstractServerTask task) {
        if (task == null) return;
        executor.submit(task);
    }

    @Override
    public void add(final Socket socket) {
        connectionService.add(socket);
    }

    @Override
    public void remove(final Socket socket) {
        connectionService.remove(socket);
    }

    @Override
    public List<Connection> connections() {
        return connectionService.connections();
    }

    public static void main(String[] args) {
        final ChatServer server = new ChatServer();
        server.run();
    }
}
