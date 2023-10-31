package controller;

import models.ChatMessage;
import view.TabPageJPanel;

import javax.swing.*;

public class TabHandler {

    private final ChatController controller;

    private JTabbedPane tab;

    public TabHandler(ChatController controller) {
        this.controller = controller;
        this.tab = controller.GetMainFrame().GetTabbedPane();
    }

    public void Handle() {
        while (true) {
            ChatMessage msg = controller.Get(null, ChatMessage.MESSAGE);
            if (controller.IsUserExist(msg.GetMessage())) {
                continue;
            }
            TabPageJPanel panel = new TabPageJPanel(controller, controller.AddUser(msg.GetMessage()) - 1);
            tab.addTab(msg.GetMessage(), panel);
            new Thread(new ReceiveThread(panel)).start();
        }
    }
}
