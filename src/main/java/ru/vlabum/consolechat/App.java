package ru.vlabum.consolechat;

import ru.vlabum.consolechat.api.ChatApp;
import ru.vlabum.consolechat.client.impl.ChatClient;
import ru.vlabum.consolechat.server.impl.ChatServer;

/**
 * consolechat
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // ChatApp - это интерфейс, общий для сервера и клиента
        final ChatApp chatApp = getApp(args);
        chatApp.run();
    }

    /**
     * В зависимости от параметра создается либо серверное приложение, либо клиентское
     * @param args аргументы командной строки, если аргумент 1 и его значение равно "server",
     *             создается серверное приложение, иначе - клиентское
     * @return объект, реализующий интерфейс ChatApp
     */
    private static ChatApp getApp(String[] args) {
        ChatApp application = null;
        if (args.length == 1 && "server".equals(args[0])) application = new ChatServer();
        else application = new ChatClient();
        return application;
    }
}
