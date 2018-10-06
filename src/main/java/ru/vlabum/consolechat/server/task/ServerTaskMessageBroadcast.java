package ru.vlabum.consolechat.server.task;

import ru.vlabum.consolechat.server.Server;
import ru.vlabum.consolechat.server.model.Connection;

public class ServerTaskMessageBroadcast extends AbstractServerTask {

    private String message;

    public ServerTaskMessageBroadcast(Server server, String message) {
        super(server);
        this.message = message;
    }

    /**
     * Пробегает по всем подключениям и рассылает сообщения
     */
    @Override
    public void run() {
        for (final Connection connection : server.connections()) {
            connection.send(message);
        }
    }
}
