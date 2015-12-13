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

import com.github.pedrovgs.androidwifiadb.Device;
import com.intellij.ui.table.JBTable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CardLayoutDevices implements ActionButtonListener {

  private static final String CARD_DEVICES = "Card with JTable devices";
  private static final String CARD_NO_DEVICES = "Card with no devices info";
  private static final String NO_DEVICE_CONNECTED = "No devices connected.";

  private Container parentContainer;
  private JPanel cards;
  private JPanel panelNoDevices;
  private JPanel panelDevices;
  private JTable tableDevices;
  private List<Device> devices;
  private DeviceAction deviceAction;

  public CardLayoutDevices(Container parentContainer, DeviceAction action) {
    this.deviceAction = action;
    this.parentContainer = parentContainer;
    this.devices = new ArrayList<Device>();
  }

  public void setDevices(List<Device> devices) {
    this.devices = devices;
  }

  public void createAndShowGUI() {
    cards = new JPanel(new CardLayout());
    createNoDevicesPanel();
    createTableDevices();
    cards.add(panelDevices, CARD_DEVICES);
    cards.add(panelNoDevices, CARD_NO_DEVICES);
    parentContainer.add(cards, BorderLayout.PAGE_START);
    setupUi();
  }

  public void updateUi() {
    if (devices != null && !devices.isEmpty()) {
      showCard(CARD_DEVICES);
      updateDevicesTable();
    } else {
      showCard(CARD_NO_DEVICES);
    }
  }

  @Override public void onConnectClick(int row) {
    Device device = getDeviceAt(row);
    if (device != null) {
      deviceAction.connectDevice(device);
    }
  }

  @Override public void onDisconnectClick(int row) {
    Device device = getDeviceAt(row);
    if (device != null) {
      deviceAction.disconnectDevice(device);
    }
  }

  private void setupUi() {
    if (devices != null && !devices.isEmpty()) {
      showCard(CARD_DEVICES);
    } else {
      showCard(CARD_NO_DEVICES);
    }
  }

  private void showCard(String cardName) {
    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, cardName);
  }

  private void createNoDevicesPanel() {
    panelNoDevices = new JPanel(new BorderLayout());
    JLabel labelNoDevices = new JLabel(NO_DEVICE_CONNECTED);
    labelNoDevices.setHorizontalAlignment(SwingConstants.CENTER);
    panelNoDevices.add(labelNoDevices);
  }

  private void createTableDevices() {
    AndroidDevicesTableModel model = new AndroidDevicesTableModel();
    for (Device device : devices) {
      model.add(device);
    }
    tableDevices = new JBTable(model);
    configureTableAppearance();

    panelDevices = new JPanel(new BorderLayout());
    panelDevices.add(tableDevices.getTableHeader(), BorderLayout.NORTH);
    panelDevices.add(tableDevices, BorderLayout.CENTER);
  }

  private void configureTableAppearance() {
    tableDevices.setOpaque(false);
    ((DefaultTableCellRenderer) tableDevices.getDefaultRenderer(Object.class)).setOpaque(false);
    tableDevices.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    tableDevices.getColumnModel().getColumn(0).setPreferredWidth(100);
    tableDevices.getColumnModel().getColumn(1).setMinWidth(85);
    tableDevices.getColumnModel().getColumn(1).setMaxWidth(85);
    tableDevices.getColumnModel().getColumn(2).setMinWidth(215);
    tableDevices.getColumnModel().getColumn(2).setMaxWidth(215);
    tableDevices.getColumnModel().getColumn(2).setCellRenderer(new ConnectDisconnectRenderer());
    tableDevices.getColumnModel().getColumn(2).setCellEditor(
        new ConnectDisconnectEditor(new JCheckBox(), this));
  }

  private void updateDevicesTable() {
    AndroidDevicesTableModel model = (AndroidDevicesTableModel) tableDevices.getModel();
    model.clear();
    for (Device device : devices) {
      model.add(device);
    }
    tableDevices.setModel(model);
    model.fireTableDataChanged();
  }

  private Device getDeviceAt(int row) {
    AndroidDevicesTableModel model = (AndroidDevicesTableModel) tableDevices.getModel();
    return model.get(row);
  }
}
