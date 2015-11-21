/*
 * Copyright (C) 2015 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pedrovgs.androidwifiadb.window;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

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
            if (ConnectDisconnectPanel.ACTION_CONNECT.equals(clickedButtonAction)) {
                listener.onConnectClick(row);
            } else if (ConnectDisconnectPanel.ACTION_DISCONNECT.equals(clickedButtonAction)) {
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