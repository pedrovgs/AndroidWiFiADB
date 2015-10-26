package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AndroidDevicesTableModel extends AbstractTableModel {
    private static final int COLUMN_DEVICE = 0;
    private static final int COLUMN_STATE = 1;
    private static final int COLUMN_ACTION = 2;

    private List<Device> devices;

    public AndroidDevicesTableModel() {
        devices = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        String value = null;
        switch (column) {
            case COLUMN_DEVICE:
                value = "Device";
                break;
            case COLUMN_STATE:
                value = "State";
                break;
            case COLUMN_ACTION:
                value = "Action";
                break;
        }
        return value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class value = Object.class;
        switch (columnIndex) {
            case COLUMN_DEVICE:
                value = String.class;
                break;
            case COLUMN_STATE:
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
            case COLUMN_DEVICE:
                value = obj.toString();
                break;
            case COLUMN_STATE:
                value = obj.isConnected() ? "connected" : "disconnected";
                break;
            case COLUMN_ACTION:
                break;
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == COLUMN_ACTION) {
            System.out.println(aValue);
            Device device = devices.get(rowIndex);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == COLUMN_ACTION;
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

    public Device get(int index) {
        if(index >= 0 && index < devices.size()) {
            return devices.get(index);
        }
        return null;
    }

}