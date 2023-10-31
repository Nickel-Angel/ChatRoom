package view;

import controller.ChatController;
import controller.ChatNoteHandler;
import controller.LogoutHandler;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame {

    private final ChatController controller;

    private LoginDialog loginDialog;
    private JTabbedPane tab;

    public ChatFrame(ChatController controller){
        super("聊天室");
        this.controller = controller;
        this.setBounds(320, 240, 580, 240);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // login
        do {
            this.loginDialog = new LoginDialog(this, this.controller);
        } while (controller.GetUsername() == null);


        // toolbar
        JToolBar toolbar = new JToolBar();
        this.getContentPane().add(toolbar, "North");

        toolbar.add(new JLabel("用户名"));
        JTextField nameField = new JTextField(this.controller.GetUsername());
        nameField.setHorizontalAlignment(JTextField.CENTER);
        toolbar.add(nameField);
        nameField.setEditable(false);

        JButton logoutButton = new JButton("离线");
        LogoutHandler logoutHandler = new LogoutHandler(this);
        logoutButton.addActionListener(logoutHandler);
        toolbar.add(logoutButton);

        // tab
        this.tab = new JTabbedPane();
        this.getContentPane().add(this.tab);
        this.tab.addChangeListener(new ChatNoteHandler(this.controller, this.tab));

        this.addWindowListener(logoutHandler);

        this.setResizable(false);
        this.setVisible(true);
    }

    public JTabbedPane GetTabbedPane() {
        return tab;
    }

    public ChatController GetController() {
        return controller;
    }
}
