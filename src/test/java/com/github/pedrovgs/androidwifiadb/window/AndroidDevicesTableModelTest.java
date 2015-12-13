/*
 *
 *  * Copyright (C) 2015 Pedro Vicente Gómez Sánchez.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.github.pedrovgs.androidwifiadb.window;

import com.github.pedrovgs.androidwifiadb.Device;
import com.github.pedrovgs.androidwifiadb.UnitTest;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class AndroidDevicesTableModelTest extends UnitTest {

  private static final int COLUMN_DEVICE = 0;
  private static final int COLUMN_STATE = 1;
  private static final int COLUMN_ACTION = 2;
  private static final int COLUMN_WITH_NOT_VALID_INDEX = -1;
  private static final String ANY_DEVICE_ID = "abcdef";
  private static final String ANY_DEVICE_NAME = "test_name";
  private static final String CONNECTED = "Connected";
  private static final String DISCONNECTED = "Disconnected";

  @Mock
  TableModelListener tableModelListener;

  @Test
  public void shouldAddDevice() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();
    assertNull(androidDevicesTableModel.get(0));

    Device device = givenAnyDevice();
    androidDevicesTableModel.add(device);

    assertEquals(device, androidDevicesTableModel.get(0));
  }

  @Test
  public void shouldClearDevicesList() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    androidDevicesTableModel.add(givenAnyDevice());

    assertEquals(1, androidDevicesTableModel.getRowCount());
    androidDevicesTableModel.clear();
    assertEquals(0, androidDevicesTableModel.getRowCount());
  }

  @Test
  public void shouldReturnValueDeviceAsStringForDeviceColumn() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    Device device = givenAnyDevice();
    androidDevicesTableModel.add(device);

    assertEquals(device.toString(), androidDevicesTableModel.getValueAt(0, COLUMN_DEVICE));
  }

  @Test
  public void shouldReturnValueConnectedStringForConnectedDevice() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    Device device = givenAnyDevice();
    device.setConnected(true);
    androidDevicesTableModel.add(device);

    assertEquals(CONNECTED, androidDevicesTableModel.getValueAt(0, COLUMN_STATE));
  }

  @Test
  public void shouldReturnValueDisconnectedStringForDisconnectedDevice() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    Device device = givenAnyDevice();
    device.setConnected(false);
    androidDevicesTableModel.add(device);

    assertEquals(DISCONNECTED, androidDevicesTableModel.getValueAt(0, COLUMN_STATE));
  }

  @Test
  public void shouldReturnNullValueForActionColumn() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    androidDevicesTableModel.add(givenAnyDevice());

    assertEquals(null, androidDevicesTableModel.getValueAt(0, COLUMN_ACTION));
  }

  @Test
  public void shouldReturnNullValueForUnknownColumn() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    androidDevicesTableModel.add(givenAnyDevice());

    assertEquals(null, androidDevicesTableModel.getValueAt(0, COLUMN_WITH_NOT_VALID_INDEX));
  }

  @Test
  public void shouldReturnEditableCellForActionColumn() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    androidDevicesTableModel.add(givenAnyDevice());

    assertFalse(androidDevicesTableModel.isCellEditable(0, COLUMN_STATE) || androidDevicesTableModel.isCellEditable(0, COLUMN_DEVICE));
    assertTrue(androidDevicesTableModel.isCellEditable(0, COLUMN_ACTION));
  }

  @Test
  public void shouldFireCellUpdateOnlyForActionColumn() throws Exception {
    AndroidDevicesTableModel androidDevicesTableModel = givenEmptyDevicesTableModel();

    androidDevicesTableModel.addTableModelListener(tableModelListener);
    androidDevicesTableModel.setValueAt(anyObject(), 0, COLUMN_ACTION);

    verify(tableModelListener, atLeastOnce()).tableChanged((TableModelEvent) anyObject());
  }

  private Device givenAnyDevice() {
    return new Device(ANY_DEVICE_NAME, ANY_DEVICE_ID);
  }

  private AndroidDevicesTableModel givenEmptyDevicesTableModel() {
    return new AndroidDevicesTableModel();
  }
}
