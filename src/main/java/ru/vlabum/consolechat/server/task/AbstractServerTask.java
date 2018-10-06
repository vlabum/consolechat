package ru.vlabum.consolechat.server.task;

import ru.vlabum.consolechat.server.Server;

public class AbstractServerTask extends Thread {

    protected final Server server;

    protected AbstractServerTask(Server server) { this.server = server; }

}
