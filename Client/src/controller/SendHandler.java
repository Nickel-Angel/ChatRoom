package controller;

import models.ChatMessage;
import view.TabPageJPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SendHandler implements ActionListener, KeyListener {

    private final ChatController controller;
    private final TabPageJPanel caller;

    private final String mainUsername;
    private JTextField textSender;
    private JTextArea textReceiver;

    public SendHandler(TabPageJPanel caller) {
        this.caller = caller;
        this.controller = caller.GetController();
        this.mainUsername = controller.GetUsername();
        this.textSender = caller.GetTextSender();
        this.textReceiver = caller.GetTextReceiver();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textSender.getText() == null || textSender.getText().isEmpty()) {
            return;
        }
        controller.Send(new ChatMessage(mainUsername, controller.GetTabUsername(caller.GetIndex()),
                ChatMessage.MESSAGE, mainUsername + " 说：" + textSender.getText()));
        textReceiver.append("我说：" + textSender.getText() + "\n");
        textSender.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (textSender.getText() == null || textSender.getText().isEmpty()) {
                return;
            }
            controller.Send(new ChatMessage(mainUsername, controller.GetTabUsername(caller.GetIndex()),
                    ChatMessage.MESSAGE, mainUsername + " 说：" + textSender.getText()));
            textReceiver.append("我说：" + textSender.getText() + "\n");
            textSender.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
