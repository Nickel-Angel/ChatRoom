package controller;

import models.ChatMessage;
import view.LoginDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginHandler implements ActionListener {

    private final ChatController controller;
    private final LoginDialog caller;

    private JTextField textUserName;
    private JTextField textPassword;

    public LoginHandler(LoginDialog caller) {
        this.caller = caller;
        this.controller = caller.GetController();
        this.textUserName = caller.GetTextUserName();
        this.textPassword = caller.GetTextPassword();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // checkNum
        if (textUserName.getText().isEmpty() || textPassword.getText().isEmpty()) {
            caller.SetLoginInfo("用户名和密码均不能为空");
            return;
        }
        if (textUserName.getText().length() > 9) {
            caller.SetLoginInfo("用户名长度不能超过 9 个字符");
            return;
        }
        if (textPassword.getText().length() > 18) {
            caller.SetLoginInfo("密码长度不能超过 18 个字符");
            return;
        }

        // send login request
        synchronized (controller) {
            controller.Send(new ChatMessage(textUserName.getText(), null, ChatMessage.QUERY, textUserName.getText()));
            controller.Send(new ChatMessage(textUserName.getText(), null, ChatMessage.QUERY, textPassword.getText()));

            /// handle message
            ChatMessage msg = controller.Get(null, ChatMessage.RESPONSE | ChatMessage.ERROR);
            if (msg.GetMessage().equals("Login success")) {
                caller.SetLoginInfo("登录成功！");
                controller.SetUsername(textUserName.getText());
                caller.dispose();
                return;
            }
            if (msg.GetMessage().equals("Password wrong")) {
                caller.SetLoginInfo("密码错误！");
                return;
            }
            if (msg.GetMessage().equals("Username must be unique")) { // waste
                caller.SetLoginInfo("该用户名已被使用！");
                return;
            }
            if (msg.GetMessage().equals("Username too long")) { // waste
                caller.SetLoginInfo("用户名过长！");
                return;
            }
            caller.SetLoginInfo(msg.GetMessage());
        }
    }
}
