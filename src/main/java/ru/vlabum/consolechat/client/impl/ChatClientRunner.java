package ru.vlabum.consolechat.client.impl;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.vlabum.consolechat.client.Client;
import ru.vlabum.consolechat.client.task.AbstractClientTask;
import ru.vlabum.consolechat.client.task.ClientTaskMessageInput;
import ru.vlabum.consolechat.client.task.ClientTaskMessageRead;
import ru.vlabum.consolechat.client.task.ClientTaskMessageSend;
import ru.vlabum.consolechat.config.ChatConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;

@Getter
public class ChatClientRunner implements Client {

    private final ChatConfig config;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final ExecutorService executor;
    private final Exchanger<String> exchangerInput;
    private final Exchanger<String> exchangerRead;

    public ChatClientRunner(final ChatConfig config, final ExecutorService executor,
                            Exchanger<String> exchangerInput,
                            Exchanger<String> exchangerRead) {
        this.config = config;
        this.executor = executor;
        this.exchangerInput = exchangerInput;
        this.exchangerRead = exchangerRead;
    }

    @Override
    @SneakyThrows
    public void run() {
        final String host = config.getHost();
        final Integer port = config.getPort();
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        run(new ClientTaskMessageRead(this, exchangerRead));
        run(new ClientTaskMessageInput(this, exchangerInput));
    }

    @Override
    public void run(final AbstractClientTask task) {
        if (task == null) return;
        executor.submit(task);
    }

    @Override
    public void broadcast(final String message) {
        if (message == null || message.isEmpty()) return;
        run(new ClientTaskMessageSend(this, message, exchangerInput));
    }

    @Override
    @SneakyThrows
    public void exit() {
        socket.close();
        System.out.println("Chat client disconnected...");
        System.exit(0);
    }
}
