package ru.vlabum.consolechat.server;

import ru.vlabum.consolechat.config.ChatConfig;
import ru.vlabum.consolechat.server.model.Connection;
import ru.vlabum.consolechat.server.task.AbstractServerTask;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

public interface Server extends Runnable {

    ExecutorService getExecutor();

    ChatConfig getConfig();

    ServerSocket getServerSocket();

    List<Connection> connections();

    void run(AbstractServerTask task);

    void add(Socket socket);

    void remove(Socket socket);
}
