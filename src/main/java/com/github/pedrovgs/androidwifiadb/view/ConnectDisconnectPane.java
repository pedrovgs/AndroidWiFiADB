package com.github.pedrovgs.androidwifiadb.view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ConnectDisconnectPane extends JPanel {
    private JButton connect;
    private JButton disconnect;
    private String state;

    public ConnectDisconnectPane() {
        setLayout(new GridBagLayout());
        connect = new JButton("Connect");
        connect.setActionCommand("connect");
        disconnect = new JButton("Disconnect");
        disconnect.setActionCommand("disconnect");

        add(connect);
        add(disconnect);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = e.getActionCommand();
                System.out.println("State = " + state);
            }
        };

        connect.addActionListener(listener);
        disconnect.addActionListener(listener);
    }

    public void addActionListener(ActionListener listener) {
        connect.addActionListener(listener);
        disconnect.addActionListener(listener);
    }

    public String getState() {
        return state;
    }
}