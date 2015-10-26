package com.github.pedrovgs.androidwifiadb.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;


import static com.github.pedrovgs.androidwifiadb.view.ConnectDisconnectPanel.ACTION_CONNECT;
import static com.github.pedrovgs.androidwifiadb.view.ConnectDisconnectPanel.ACTION_DISCONNECT;

public class ConnectDisconnectEditor extends DefaultCellEditor {
    private boolean clicked;
    private String clickedButtonAction;
    private int row, col;
    private ConnectDisconnectPanel buttonsPanel;
    private ActionButtonListener listener;

    public ConnectDisconnectEditor(JCheckBox checkBox, ActionButtonListener listener) {
        super(checkBox);
        this.listener = listener;
        buttonsPanel = new ConnectDisconnectPanel();
        buttonsPanel.addActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedButtonAction = e.getActionCommand();
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        this.col = column;

        clicked = true;
        return buttonsPanel;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked && listener != null) {
            if(ACTION_CONNECT.equals(clickedButtonAction)) {
                listener.onConnectClick(row);
            }else if(ACTION_DISCONNECT.equals(clickedButtonAction)) {
                listener.onDisconnectClick(row);
            }
        }
        clickedButtonAction = "";
        clicked = false;
        return "";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}