package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;
import com.intellij.ui.table.JBTable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * Created by vgaidarji on 10/26/15.
 */
public class CardLayoutDevices {

    private static final String CARD_DEVICES = "Card with JTable devices";
    private static final String CARD_NO_DEVICES = "Card with no devices info";

    private Container parentContainer;
    private JPanel cards;
    private JPanel panelNoDevices;
    private JPanel panelDevices;
    private JTable tableDevices;
    private List<Device> devices;

    public CardLayoutDevices(Container parentContainer) {
        this.parentContainer = parentContainer;
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
        updateUi();
    }

    /**
     * Shows appropriate card (depends on connected devices).
     */
    private void updateUi() {
        if(devices.size() > 0) {
            showCard(CARD_DEVICES);
        }else {
            showCard(CARD_NO_DEVICES);
        }
    }

    private void showCard(String cardName) {
        CardLayout cardLayout = (CardLayout)(cards.getLayout());
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
        for(Device device : devices) {
            model.add(device);
        }
        tableDevices = new JBTable(model);
        ConnectDisconnectRenderer renderer = new ConnectDisconnectRenderer();
        tableDevices.getColumnModel().getColumn(2).setCellRenderer(renderer);

        panelDevices = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelDevices.add(tableDevices, gbc);
    }
}
