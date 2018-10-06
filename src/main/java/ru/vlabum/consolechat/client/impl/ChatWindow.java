package ru.vlabum.consolechat.client.impl;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Exchanger;

/**
 * Окно чата
 */
@Getter
public class ChatWindow extends JFrame implements Runnable {

    private final JTextArea textAreaChat = getTextAreaChat("Mental Chat");
    private final JTextField textFieldSend = getTextFieldSend();
    private final Exchanger<String> exchangerInput;
    private final Exchanger<String> exchangerRead;
    public boolean read = true;

    public ChatWindow(Exchanger<String> exchangerInput, Exchanger<String> exchangerRead) {
        this.exchangerInput = exchangerInput;
        this.exchangerRead = exchangerRead;
        setTitle("Mental Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        final JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));
        sendPanel.add(textFieldSend);
        final JButton buttonSend = getButtonSend();
        sendPanel.add(buttonSend);

        final JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(textAreaChat, BorderLayout.CENTER);
        panel.add(sendPanel, BorderLayout.SOUTH);
        add(panel);

        Thread listener = new Thread(this);
        listener.start();
    }

    /**
     * Создание кнопки, которая отсылает сообщение
     * @return - ссылка на созданный объект JButton
     */
    private JButton getButtonSend() {
        final JButton button = new JButton("Send");
        button.addActionListener(event -> send());
        return button;
    }

    /**
     * Создание общего поля чата
     * @param caption - приветствие
     * @return - ссылка на созданный объект JTextArea
     */
    private JTextArea getTextAreaChat(String caption) {
        final JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.append(caption);
        textArea.setMargin(new Insets(5,5,5,5));
        return textArea;
    }

    /**
     * Создание поля для ввода сообщений
     * @return - ссылка на созданный объект JTextField
     */
    private JTextField getTextFieldSend() {
        final JTextField textField = new JTextField();
        textField.addActionListener(event -> send());
        return textField;
    }

    /**
     * Отсылка сообщения
     */
    private void send() {
        final String message = textFieldSend.getText();
        if (message.isEmpty()) {
            textFieldSend.requestFocus();
            return;
        }
        textFieldSend.setText("");
        textFieldSend.requestFocus();
        try {
            exchangerInput.exchange(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * слушаем и выводим пользователю сообщения, пришедшие с сервера
     */
    public void appendMessages() {
        String message;
        try {
            message = exchangerRead.exchange("!");
            textAreaChat.append("\n" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (read)
            appendMessages();
    }
}
