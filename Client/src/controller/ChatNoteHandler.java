package controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChatNoteHandler implements ChangeListener {

    private final ChatController controller;
    private final JTabbedPane caller;

    public ChatNoteHandler(ChatController controller, JTabbedPane caller) {
        this.controller = controller;
        this.caller = caller;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int index = ((JTabbedPane) e.getSource()).getSelectedIndex();
        caller.setTitleAt(index, controller.GetTabUsername(index)); // update title
    }
}
