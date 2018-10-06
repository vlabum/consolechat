package ru.vlabum.consolechat.server.service;

import ru.vlabum.consolechat.api.ConnectionService;
import ru.vlabum.consolechat.server.Server;
import ru.vlabum.consolechat.server.model.Connection;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Управляет списком подключений
 */
public class ConnectionServiceBean implements ConnectionService {

    private final Server server;

    private final List<Connection> connections = new ArrayList<>();

    public ConnectionServiceBean(final Server server) { this.server = server; }

    public List<Connection> connections() { return connections; }

    /**
     * Добавляет подключение
     * @param socket - сокет
     */
    public void add(final Socket socket) {
        if (socket == null) return;
        final Connection connection = new Connection(server, socket);
        connections.add(connection);
        System.out.println("Added connection with ID = " + connection.getId());
    }

    /**
     * Поиск подключения
     * @param socket - сокет
     * @return
     */
    public Connection get(final Socket socket) {
        if (socket == null) return null;
        for (final Connection connection : connections) {
            if (connection.getSocket().equals(socket)) return connection;
        }
        return null;
    }

    /**
     * Удаляет подключение
     * @param socket - сокет
     */
    public void remove(final Socket socket) {
        if (socket == null) return;
        final Connection connection = get(socket);
        connections.remove(connection);
        System.out.println("Removed connection with id = " + connection.getId());
    }
}
