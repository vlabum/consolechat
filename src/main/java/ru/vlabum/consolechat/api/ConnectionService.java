package ru.vlabum.consolechat.api;

import ru.vlabum.consolechat.server.model.Connection;

import java.net.Socket;
import java.util.List;

public interface ConnectionService {

    List<Connection> connections();

    Connection get(final Socket socket);

    void add(final Socket socket);

    void remove(final Socket socket);
}
