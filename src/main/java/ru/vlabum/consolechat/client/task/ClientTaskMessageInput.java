package ru.vlabum.consolechat.client.task;

import ru.vlabum.consolechat.client.Client;

import java.util.concurrent.Exchanger;

public class ClientTaskMessageInput extends AbstractClientTask {

    private final static String CMD_EXIT = "exit";

    public ClientTaskMessageInput(final Client client, Exchanger<String> exchanger) {
        super(client, exchanger);
    }

    @Override
    public void run() {
        System.out.println("Enter cmd (message or exit)");
        String message = "";
        try {
            message = exchanger.exchange("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CMD_EXIT.equals(message)) {
            client.exit();
            return;
        }
        client.broadcast(message);
        System.out.println();
        client.run(new ClientTaskMessageInput(client, exchanger));
    }
}
