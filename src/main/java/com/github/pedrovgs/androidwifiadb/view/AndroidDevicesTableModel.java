package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AndroidDevicesTableModel extends AbstractTableModel {
    private List<Device> devices;

    public AndroidDevicesTableModel() {
        devices = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        String value = null;
        switch (column) {
            case 0:
                value = "Device";
                break;
            case 1:
                value = "State";
                break;
            case 2:
                value = "Action";
                break;
        }
        return value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class value = Object.class;
        switch (columnIndex) {
            case 0:
                value = String.class;
                break;
            case 1:
                value = String.class;
                break;
        }
        return value;
    }

    @Override
    public int getRowCount() {
        return devices.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Device obj = devices.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = obj.toString();
                break;
            case 1:
                value = obj.isConnected() ? "connected" : "disconnected";
                break;
            case 2:
                break;
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            System.out.println(aValue);
            Device device = devices.get(rowIndex);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    /**
     * Clear data source.
     */
    public void clear() {
        if(devices != null) {
            devices.clear();
        }
    }

    public void add(Device value) {
        int startIndex = getRowCount();
        devices.add(value);
        fireTableRowsInserted(startIndex, getRowCount() - 1);
    }

    public void remove(Device value) {
        int startIndex = devices.indexOf(value);
        System.out.println("startIndex = " + startIndex);
        devices.remove(value);
        fireTableRowsInserted(startIndex, startIndex);
    }
}