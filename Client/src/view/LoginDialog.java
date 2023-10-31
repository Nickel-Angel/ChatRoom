package view;

import controller.ChatController;
import controller.LoginHandler;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private final ChatFrame parent;
    private final ChatController controller;

    private JTextField textUserName;
    private JTextField textPassword;
    private JLabel loginInfo;

    public LoginDialog(ChatFrame parent, ChatController controller) {
        this.parent = parent;
        this.controller = controller;

        setTitle("登录或注册");

        setVisible(true);
        setModal(true);

        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel inputArea = new JPanel(new GridLayout(2, 2));

        inputArea.add(new JLabel("用户名"));
        this.textUserName = new JTextField(9);
        inputArea.add(this.textUserName);
        this.textUserName.setHorizontalAlignment(JTextField.CENTER);

        inputArea.add(new JLabel("密码"));
        this.textPassword = new JTextField(18);
        inputArea.add(this.textPassword);
        this.textPassword.setHorizontalAlignment(JTextField.CENTER);

        JToolBar bottomToolbar = new JToolBar();

        this.loginInfo = new JLabel("", JLabel.CENTER);
        bottomToolbar.add(this.loginInfo);
        loginInfo.setText("新用户可直接在此页面注册");

        JButton loginConfirm = new JButton("登录/注册");
        loginConfirm.addActionListener(new LoginHandler(this));
        bottomToolbar.add(loginConfirm);

        add(inputArea, BorderLayout.CENTER);
        add(bottomToolbar, BorderLayout.SOUTH);

        while (controller.GetUsername() == null) {
            Thread.onSpinWait();
        }
        if (controller.GetUsername() != null) {
            System.out.println("Client username: " + controller.GetUsername());
        }
    }

    public ChatController GetController() {
        return controller;
    }

    public void SetLoginInfo(String info) {
        loginInfo.setText(info);
    }

    public JTextField GetTextUserName() {
        return textUserName;
    }

    public JTextField GetTextPassword() {
        return textPassword;
    }
}
