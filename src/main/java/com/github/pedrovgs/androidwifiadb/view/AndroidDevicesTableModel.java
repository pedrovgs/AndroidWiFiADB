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
    devices = new ArrayList<Device>();
  }

  @Override
  public String getColumnName(int column) {
    String value;
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
      default:
        return null;
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
      default:
        return value;
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
      default:
        return null;
    }
    return value;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (columnIndex == COLUMN_ACTION) {
      fireTableCellUpdated(rowIndex, columnIndex);
    }
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return columnIndex == COLUMN_ACTION;
  }

  public void clear() {
    if (devices != null) {
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
    devices.remove(value);
    fireTableRowsInserted(startIndex, startIndex);
  }

  public Device get(int index) {
    if (index >= 0 && index < devices.size()) {
      return devices.get(index);
    }
    return null;
  }
}