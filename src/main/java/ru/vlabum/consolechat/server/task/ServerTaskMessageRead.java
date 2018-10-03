package ru.vlabum.consolechat.server.task;

import ru.vlabum.consolechat.server.Server;

import java.io.DataInputStream;
import java.net.Socket;

public class ServerTaskMessageRead extends AbstractServerTask {

    private Socket socket;

    public ServerTaskMessageRead(final Server server, final Socket socket) {
        super(server);
        this.socket = socket;
    }

    /**
     * Первично запущенный из ServerTaskConnection, считывает сообщение,
     * создает 2 потока:
     *      1) server.run(ServerTaskMessageRead) - Ожидание сообщений с текущего сокета
     *      2) server.run(ServerTaskMessageBroadcast) - Рассылка всем подключениям полученного сообщения
     *      Затем завершает работу.
     */
    @Override
    public void run() {
        try {
            System.out.println("in.ReadUTF");
            final DataInputStream in = new DataInputStream(socket.getInputStream());
            final String message = in.readUTF();
            System.out.println(message);
            AbstractServerTask taskRead = new ServerTaskMessageRead(server, socket);
            server.run(taskRead);
            AbstractServerTask taskBroadcast = new ServerTaskMessageBroadcast(server, message);
            server.run(taskBroadcast);
        } catch (Exception e) {
            server.remove(socket);
        }
    }
}