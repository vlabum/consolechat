package ru.vlabum.consolechat.api;

import ru.vlabum.consolechat.config.ChatConfig;

import java.util.concurrent.ExecutorService;

/**
 * Интерфейс для сервера и клиента.
 */
public interface ChatApp extends Runnable {

    ExecutorService getExecutor();

    ChatConfig getConfig();

    void run();
}
