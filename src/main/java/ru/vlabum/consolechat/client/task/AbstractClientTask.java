package ru.vlabum.consolechat.client.task;

import ru.vlabum.consolechat.client.Client;

import java.util.concurrent.Exchanger;

public class AbstractClientTask extends Thread {

    final Client client;
    Exchanger<String> exchanger;

    public AbstractClientTask(final Client client, Exchanger<String> exchanger) {
        this.client = client;
        this.exchanger = exchanger;
    }
}
