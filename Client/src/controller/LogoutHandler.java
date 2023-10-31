package controller;

import models.ChatMessage;
import view.ChatFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LogoutHandler implements ActionListener, WindowListener {

    private final ChatController controller;
    private final ChatFrame caller;

    private final String mainUsername;

    public LogoutHandler(ChatFrame caller) {
        this.caller = caller;
        this.controller = caller.GetController();
        this.mainUsername = controller.GetUsername();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.Send(new ChatMessage(mainUsername, null, ChatMessage.MESSAGE, "Logout"));
        ChatMessage msg = controller.Get(null, ChatMessage.MESSAGE);
        controller.Close();
        caller.dispose();
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        controller.Send(new ChatMessage(mainUsername, null, ChatMessage.MESSAGE, "Logout"));
        ChatMessage msg = controller.Get(null, ChatMessage.MESSAGE);
        controller.Close();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
