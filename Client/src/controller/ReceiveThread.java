package controller;

import models.ChatMessage;
import view.ChatFrame;
import view.TabPageJPanel;

import javax.swing.*;

public class ReceiveThread implements Runnable {

    private final ChatController controller;
    private final ChatFrame mainFrame;
    private final TabPageJPanel caller;
    private JTextArea textReceiver;

    private String toName;

    public ReceiveThread(TabPageJPanel caller) {
        this.caller = caller;
        this.controller = caller.GetController();
        this.mainFrame = controller.GetMainFrame();
        this.textReceiver = caller.GetTextReceiver();
        this.toName = caller.GetToName();
    }

    @Override
    public void run() {
        synchronized (caller) {
            caller.GetSendButton().setEnabled(true);
            ChatMessage msg;
            while ((msg = controller.Get(toName, ChatMessage.MESSAGE | ChatMessage.RESPONSE)) != null
                    && msg.GetType() == ChatMessage.MESSAGE) {
                if (mainFrame.GetTabbedPane().getSelectedIndex() != caller.GetIndex()) {
                    mainFrame.GetTabbedPane().setTitleAt(caller.GetIndex(), "[*] " + toName);
                }
                textReceiver.append(msg.GetMessage() + "\r\n");
            }
            caller.GetSendButton().setEnabled(false);
        }
    }
}
