package ru.vlabum.consolechat.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatConfig {

    private final Integer port = 8080 ;

    private final Integer threads = 3;

    private final String host = "localhost";
}
