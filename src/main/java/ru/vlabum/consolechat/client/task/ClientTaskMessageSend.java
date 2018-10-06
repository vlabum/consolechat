package ru.vlabum.consolechat.client.task;

import ru.vlabum.consolechat.client.Client;

import java.util.concurrent.Exchanger;

public class ClientTaskMessageSend extends AbstractClientTask {

    private String message;

    public ClientTaskMessageSend(Client client, String message, Exchanger<String> exchanger) {
        super(client, exchanger);
        this.message = message;
    }

    @Override
    public void run() {
        try {
            client.getOut().writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
            client.exit();
        }
    }
}
