package ru.vlabum.consolechat.server.task;

import lombok.SneakyThrows;
import ru.vlabum.consolechat.server.Server;

import java.net.Socket;

/**
 * Задача обработки подключения клиента к серверу
 */
public class ServerTaskConnection extends AbstractServerTask {

    public ServerTaskConnection(final Server server) {
        super(server);
    }

    /**
     * Создаем сокет и ждем к нему подключения. Как только дожидаемся подключения,
     * создаем еще один поток с такой же задачей, чтобы другие могли подключиться.
     * Созданный сокет отдаем объекту ChatServerRunner, посредством ConnectionServiceBean сохранит в списке подключений
     * Тут же создаем задачу на прослушивание созданного сокета и запускаем ее в отдельном потоке.
     * т.о. первоначально вызванный из App server.run()
     * вызывает server.run(ServerTaskConnection), который запускает еще 2 потока:
     *  1) server.run(ServerTaskConnection) - Ожидание нового подключения
     *  2) server.run(ServerTaskMessageRead) - Ожидание по созданному сокету сообщений
     *  Затем этот поток завершается.
     */
    @Override
    @SneakyThrows
    public void run() {
        System.out.println("getServerSocket().accept()");
        final Socket socket = server.getServerSocket().accept();
        AbstractServerTask taskConnect = new ServerTaskConnection(server);
        server.run(taskConnect);
        AbstractServerTask taskRead = new ServerTaskMessageRead(server, socket);
        server.run(taskRead);
        server.add(socket);
    }
}
