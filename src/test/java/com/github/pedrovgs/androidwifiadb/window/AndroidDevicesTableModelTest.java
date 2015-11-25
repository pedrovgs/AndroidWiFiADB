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
  private static final String CONNECTED = "connected";
  private static final String DISCONNECTED = "disconnected";

  @Mock
  TableModelListener tableModelListener;

  @Test
  public void shouldAddDevice() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();
    assertNull(sut.get(0));

    Device device = givenAnyDevice();
    sut.add(device);

    assertEquals(device, sut.get(0));
  }

  @Test
  public void shouldClearDevicesList() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    sut.add(givenAnyDevice());

    assertEquals(1, sut.getRowCount());
    sut.clear();
    assertEquals(0, sut.getRowCount());
  }

  @Test
  public void shouldReturnValueDeviceAsStringForDeviceColumn() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    Device device = givenAnyDevice();
    sut.add(device);

    assertEquals(device.toString(), sut.getValueAt(0, COLUMN_DEVICE));
  }

  @Test
  public void shouldReturnValueConnectedStringForConnectedDevice() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    Device device = givenAnyDevice();
    device.setConnected(true);
    sut.add(device);

    assertEquals(CONNECTED, sut.getValueAt(0, COLUMN_STATE));
  }

  @Test
  public void shouldReturnValueDisconnectedStringForDisconnectedDevice() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    Device device = givenAnyDevice();
    device.setConnected(false);
    sut.add(device);

    assertEquals(DISCONNECTED, sut.getValueAt(0, COLUMN_STATE));
  }

  @Test
  public void shouldReturnNullValueForActionColumn() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    sut.add(givenAnyDevice());

    assertEquals(null, sut.getValueAt(0, COLUMN_ACTION));
  }

  @Test
  public void shouldReturnNullValueForUnknownColumn() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    sut.add(givenAnyDevice());

    assertEquals(null, sut.getValueAt(0, COLUMN_WITH_NOT_VALID_INDEX));
  }

  @Test
  public void shouldReturnEditableCellForActionColumn() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    sut.add(givenAnyDevice());

    assertFalse(sut.isCellEditable(0, COLUMN_STATE) || sut.isCellEditable(0, COLUMN_DEVICE));
    assertTrue(sut.isCellEditable(0, COLUMN_ACTION));
  }

  @Test
  public void shouldFireCellUpdateOnlyForActionColumn() throws Exception {
    AndroidDevicesTableModel sut = givenEmptyDevicesTableModel();

    sut.addTableModelListener(tableModelListener);
    sut.setValueAt(anyObject(), 0, COLUMN_ACTION);

    verify(tableModelListener, atLeastOnce()).tableChanged((TableModelEvent) anyObject());
  }

  private Device givenAnyDevice() {
    return new Device(ANY_DEVICE_NAME, ANY_DEVICE_ID);
  }

  private AndroidDevicesTableModel givenEmptyDevicesTableModel() {
    return new AndroidDevicesTableModel();
  }
}
