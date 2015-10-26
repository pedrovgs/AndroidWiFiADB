package com.github.pedrovgs.androidwifiadb.view;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ConnectDisconnectPanel extends JPanel {
    public static final String ACTION_CONNECT = "Connect";
    public static final String ACTION_DISCONNECT = "Disconnect";

    private JButton connect;
    private JButton disconnect;

    public ConnectDisconnectPanel() {
        setLayout(new GridBagLayout());
        connect = new JButton(ACTION_CONNECT);
        connect.setActionCommand(ACTION_CONNECT);
        connect.setOpaque(true);
        disconnect = new JButton(ACTION_DISCONNECT);
        disconnect.setActionCommand(ACTION_DISCONNECT);
        disconnect.setOpaque(true);

        add(connect);
        add(disconnect);
    }

    public void addActionListeners(ActionListener listener) {
        connect.addActionListener(listener);
        disconnect.addActionListener(listener);
    }
}