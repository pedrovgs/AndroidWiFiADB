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

package com.github.pedrovgs.androidwifiadb;

import com.github.pedrovgs.androidwifiadb.adb.ADB;
import com.github.pedrovgs.androidwifiadb.model.Device;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AndroidWiFiADBTest extends UnitTest {

  private static final int SOME_DEVICES = 4;
  private static final String ANY_DEVICE_NAME = "Device nº ";

  @Mock private ADB adb;
  @Mock private View view;

  @Test public void shouldShowErrorIfADBIsNotInstalled() {
    AndroidWiFiADB sut = givenAnAndroidWiFiADB();
    givenADBIsNotInstalled();

    sut.connectDevices();

    verify(view).showADBNotInstalledNotification();
  }

  @Test public void shouldShowNoConnectedDevicesNotificationIfThereAreNotConnectedDevicesByUSB() {
    AndroidWiFiADB sut = givenAnAndroidWiFiADB();
    givenThereAreNoConnectedDevices();

    sut.connectDevices();

    verify(view).showNoConnectedDevicesNotification();
  }

  @Test public void shouldShowDevicesConnectedIfADBWiFiWhenConnectionIsEstablished() {
    AndroidWiFiADB sut = givenAnAndroidWiFiADB();
    List<Device> devices = givenThereAreSomeDevicesConnectedByUSB();
    givenDevicesAreConnectedSuccessfully(devices);

    sut.connectDevices();

    for (Device device : devices) {
      verify(view).showConnectedDeviceNotification(device);
    }
  }

  @Test public void shouldShowDeviceConnectionErrorWhenConnectionIsNotEstablished() {
    AndroidWiFiADB sut = givenAnAndroidWiFiADB();
    List<Device> devices = givenThereAreSomeDevicesConnectedByUSB();
    givenDevicesAreNotConnectedSuccessfully(devices);

    sut.connectDevices();

    for (Device device : devices) {
      verify(view).showErrorConnectingDeviceNotification(device);
    }
  }

  private void givenDevicesAreNotConnectedSuccessfully(List<Device> devices) {
    for (Device device : devices) {
      device.setConnected(false);
    }
    when(adb.getDevicesConnectedByUSB()).thenReturn(devices);
    when(adb.connectDevices(devices)).thenReturn(devices);
  }

  private void givenDevicesAreConnectedSuccessfully(List<Device> devices) {
    for (Device device : devices) {
      device.setConnected(true);
    }
    when(adb.getDevicesConnectedByUSB()).thenReturn(devices);
    when(adb.connectDevices(devices)).thenReturn(devices);
  }

  private List<Device> givenThereAreSomeDevicesConnectedByUSB() {
    when(adb.isInstalled()).thenReturn(true);
    List<Device> devices = new LinkedList<Device>();
    for (int i = 0; i < SOME_DEVICES; i++) {
      String name = ANY_DEVICE_NAME + i;
      String id = String.valueOf(i);
      Device device = new Device(name, id);
      devices.add(device);
    }
    return devices;
  }

  private void givenThereAreNoConnectedDevices() {
    when(adb.isInstalled()).thenReturn(true);
    when(adb.getDevicesConnectedByUSB()).thenReturn(new LinkedList<Device>());
  }

  private void givenADBIsNotInstalled() {
    when(adb.isInstalled()).thenReturn(false);
  }

  private AndroidWiFiADB givenAnAndroidWiFiADB() {
    return new AndroidWiFiADB(adb, view);
  }
}
