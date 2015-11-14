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