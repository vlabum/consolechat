package ru.vlabum.consolechat.client.impl;

import lombok.Getter;
import ru.vlabum.consolechat.api.ChatApp;
import ru.vlabum.consolechat.client.Client;
import ru.vlabum.consolechat.config.ChatConfig;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class ChatClient implements ChatApp {

    private final ChatConfig config;
    private final ExecutorService executor;
    private final Client client;
    private final Exchanger<String> exchangerInput;
    private final Exchanger<String> exchangerRead;
    private final ChatWindow chatWindow;

    public ChatClient() {
        config = new ChatConfig();
        executor = Executors.newCachedThreadPool();
        exchangerInput = new Exchanger<>();
        exchangerRead = new Exchanger<>();
        client = new ChatClientRunner(config, executor, exchangerInput, exchangerRead);
        chatWindow = new ChatWindow(exchangerInput, exchangerRead);
    }

    public void run() {
        chatWindow.setVisible(true);
        client.run();
    }

}
