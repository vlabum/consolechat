package ru.vlabum.consolechat.server.impl;

import lombok.Getter;
import ru.vlabum.consolechat.api.ChatApp;
import ru.vlabum.consolechat.config.ChatConfig;
import ru.vlabum.consolechat.server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Приложение-сервер
 */
@Getter
public class ChatServer implements ChatApp {
    private final ChatConfig config;
    private final ExecutorService executor;
    private final Server server;

    public ChatServer() {
        config = new ChatConfig();
        executor = Executors.newCachedThreadPool();
        server = new ChatServerRunner(config, executor);
    }

    @Override
    public void run() { server.run(); }

    public static void main(String[] args) {
        final ChatServer server = new ChatServer();
        server.run();
    }
}
