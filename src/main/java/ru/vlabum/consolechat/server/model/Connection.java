package ru.vlabum.consolechat.server.model;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.vlabum.consolechat.server.Server;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Одно подключение, определяемое конкретным сервером и сокетом
 * и которе может отправлять в stream строку
 * В дальнейшем этот класс может развиться и отвечать за пользователя
 */
@Getter
public class Connection  {

    private final String id = UUID.randomUUID().toString();

    private final Server server;

    private final Socket socket;

    public Connection(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @SneakyThrows
    public void send(final String message) {
        final DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        stream.writeUTF(message);
    }
}
