package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;
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

/**
 * Created by vgaidarji on 10/26/15.
 */
public class CardLayoutDevices implements ActionButtonListener {

    private static final String CARD_DEVICES = "Card with JTable devices";
    private static final String CARD_NO_DEVICES = "Card with no devices info";

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

    /**
     * Create and show Devices UI in parentContainer.
     */
    public void createAndShowGUI() {
        cards = new JPanel(new CardLayout());
        createNoDevicesPanel();
        createTableDevices();
        cards.add(panelDevices, CARD_DEVICES);
        cards.add(panelNoDevices, CARD_NO_DEVICES);
        parentContainer.add(cards, BorderLayout.PAGE_START);
        setupUi();
    }

    private void setupUi() {
        if (devices != null && devices.size() > 0) {
            showCard(CARD_DEVICES);
        } else {
            showCard(CARD_NO_DEVICES);
        }
    }

    /**
     * Shows appropriate card, depending on connected devices.
     */
    public void updateUi() {
        if (devices != null && devices.size() > 0) {
            showCard(CARD_DEVICES);
            updateDevicesTable();
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
        JLabel labelNoDevices = new JLabel("No devices connected.");
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

    @Override
    public void onConnectClick(int row) {
        Device device = getDeviceAt(row);
        if (device != null) {
            deviceAction.connectDevice(device);
        }
    }

    @Override
    public void onDisconnectClick(int row) {
        Device device = getDeviceAt(row);
        if (device != null) {
            deviceAction.disconnectDevice(device);
        }
    }

    private Device getDeviceAt(int row) {
        AndroidDevicesTableModel model = (AndroidDevicesTableModel) tableDevices.getModel();
        return model.get(row);
    }
}
