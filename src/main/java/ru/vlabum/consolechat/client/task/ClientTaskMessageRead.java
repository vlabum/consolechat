package ru.vlabum.consolechat.client.task;

import ru.vlabum.consolechat.client.Client;

import java.util.concurrent.Exchanger;

public class ClientTaskMessageRead extends AbstractClientTask {

    public ClientTaskMessageRead(final Client client, Exchanger<String> exchanger) {
        super(client, exchanger);
    }

    @Override
    public void run() {
        try {
            final String message = client.getIn().readUTF();
            try {
                exchanger.exchange(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("***" + message + "***");
            client.run(new ClientTaskMessageRead(client, exchanger));
        } catch (Exception e) {
            e.printStackTrace();
            client.exit();
        }
    }

}
