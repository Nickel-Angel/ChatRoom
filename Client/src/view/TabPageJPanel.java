package view;

import controller.ChatController;
import controller.SendHandler;

import javax.swing.*;
import java.awt.*;

public class TabPageJPanel extends JPanel {

    private final ChatController controller;

    private final int indexInController;
    private final String toName;

    private JTextArea textReceiver;
    private JTextField textSender;
    private JButton sendButton;

    public TabPageJPanel(ChatController controller, int indexInController) {
        super(new BorderLayout());

        this.controller = controller;
        this.indexInController = indexInController;
        this.toName = controller.GetTabUsername(indexInController);

        this.textReceiver = new JTextArea();
        this.add(new JScrollPane(this.textReceiver));
        this.textReceiver.setEditable(false);

        JToolBar toolbar = new JToolBar();
        this.add(toolbar, "South");

        this.textSender = new JTextField(16);
        toolbar.add(this.textSender);
        SendHandler sendHandler = new SendHandler(this);
        this.textSender.addActionListener(sendHandler);

        this.sendButton = new JButton("发送");
        toolbar.add(this.sendButton);
        this.sendButton.addActionListener(sendHandler);
        this.sendButton.addKeyListener(sendHandler);
    }

    public ChatController GetController() {
        return controller;
    }

    public int GetIndex() {
        return indexInController;
    }

    public JTextField GetTextSender() {
        return textSender;
    }

    public JTextArea GetTextReceiver() {
        return textReceiver;
    }

    public String GetToName() {
        return toName;
    }

    public JButton GetSendButton() {
        return sendButton;
    }
}
