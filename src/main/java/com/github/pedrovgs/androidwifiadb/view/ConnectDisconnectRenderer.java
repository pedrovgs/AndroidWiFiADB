package com.github.pedrovgs.androidwifiadb.view;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ConnectDisconnectRenderer extends DefaultTableCellRenderer {
    private ConnectDisconnectPane accconnectDisconnectPane;

    public ConnectDisconnectRenderer() {
        accconnectDisconnectPane = new ConnectDisconnectPane();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            accconnectDisconnectPane.setBackground(table.getSelectionBackground());
        } else {
            accconnectDisconnectPane.setBackground(table.getBackground());
        }
        return accconnectDisconnectPane;
    }
}