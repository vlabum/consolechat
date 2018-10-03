package ru.vlabum.consolechat.client;

import ru.vlabum.consolechat.client.task.AbstractClientTask;
import ru.vlabum.consolechat.config.ChatConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public interface Client extends Runnable{

    ExecutorService getExecutor();

    ChatConfig getConfig();

    Socket getSocket();

    DataInputStream getIn();

    DataOutputStream getOut();

    void broadcast(String message);

    void run();

    void run(AbstractClientTask task);

    void exit();

}
