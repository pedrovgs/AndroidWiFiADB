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
import com.github.pedrovgs.androidwifiadb.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AndroidWiFiADBTest extends UnitTest {

  private static final int SOME_DEVICES = 4;
  private static final String ANY_DEVICE_NAME = "Device nº ";
  private static final String ANY_DEVICE_ID = "abcdef123";
  private static final String ANY_DEVICE_IP = "0.0.0.0";

  @Mock private ADB adb;
  @Mock private View view;

  @Test public void shouldShowErrorIfADBIsNotInstalled() {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    givenADBIsNotInstalled();

    androidWiFiAdb.connectDevices();

    verify(view).showADBNotInstalledNotification();
  }

  @Test public void shouldShowNoConnectedDevicesNotificationIfThereAreNotConnectedDevicesByUSB() {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    givenThereAreNoConnectedDevices();

    androidWiFiAdb.connectDevices();

    verify(view).showNoConnectedDevicesNotification();
  }

  @Test public void shouldShowDevicesConnectedIfADBWiFiWhenConnectionIsEstablished() {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    List<Device> devices = givenThereAreSomeDevicesConnectedByUSB();
    givenDevicesAreConnectedSuccessfully(devices);

    androidWiFiAdb.connectDevices();

    for (Device device : devices) {
      verify(view).showConnectedDeviceNotification(device);
    }
  }

  @Test public void shouldShowDeviceConnectionErrorWhenConnectionIsNotEstablished() {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    List<Device> devices = givenThereAreSomeDevicesConnectedByUSB();
    givenDevicesAreNotConnectedSuccessfully(devices);

    androidWiFiAdb.connectDevices();

    for (Device device : devices) {
      verify(view).showErrorConnectingDeviceNotification(device);
    }
  }

  @Test
  public void shouldNotRefreshDevicesListIfAdbIsNotIstalled() throws Exception {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    givenADBIsNotInstalled();

    assertFalse(androidWiFiAdb.refreshDevicesList());
  }

  @Test
  public void shouldRefreshDevicesListAddNewDevice() throws Exception {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    List<Device> devices = givenThereAreSomeDevicesConnectedByUSB();
    givenDevicesAreConnectedSuccessfully(devices);
    givenAnyIpToDevices();

    assertEquals(0, androidWiFiAdb.getDevices().size());
    androidWiFiAdb.refreshDevicesList();
    assertEquals(devices.size(), androidWiFiAdb.getDevices().size());
  }

  @Test
  public void shouldRefreshDevicesListUpdateExistingDevices() throws Exception {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    List<Device> devices = givenThereAreSomeDevicesConnectedByUSB();
    givenDevicesAreConnectedSuccessfully(devices);

    androidWiFiAdb.connectDevices();
    androidWiFiAdb.refreshDevicesList();

    assertEquals(devices.size(), androidWiFiAdb.getDevices().size());
  }

  @Test
  public void shouldDisconnectDevice() throws Exception {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    givenADBIsInstalled();
    Device device = givenAnyConnectedDevice();
    givenDevicesAreDisconnectedSuccessfully(Arrays.asList(device));

    androidWiFiAdb.disconnectDevice(device);

    assertFalse(device.isConnected());
  }

  @Test
  public void shouldConnectDevice() throws Exception {
    AndroidWiFiADB androidWiFiAdb = givenAnAndroidWiFiADB();
    givenADBIsInstalled();
    Device device = givenAnyDisonnectedDevice();
    givenDevicesAreConnectedSuccessfully(Arrays.asList(device));

    androidWiFiAdb.connectDevice(device);

    assertTrue(device.isConnected());
  }

  private Device givenAnyConnectedDevice() {
    Device device = new Device(ANY_DEVICE_NAME, ANY_DEVICE_ID);
    device.setConnected(true);
    return device;
  }

  private Device givenAnyDisonnectedDevice() {
    return new Device(ANY_DEVICE_NAME, ANY_DEVICE_ID);
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
    when(adb.connectDevices(devices)).thenReturn(new ArrayList<Device>(devices));
  }

  private void givenDevicesAreDisconnectedSuccessfully(final List<Device> devices) {
    for (Device device : devices) {
      device.setConnected(false);
    }
    when(adb.disconnectDevices(devices)).thenReturn(devices);
  }

  private List<Device> givenThereAreSomeDevicesConnectedByUSB() {
    when(adb.isInstalled()).thenReturn(true);
    return getSomeDevices(SOME_DEVICES);
  }

  private List<Device> getSomeDevices(int devicesCount) {
    List<Device> devices = new LinkedList<Device>();
    for (int i = 0; i < devicesCount; i++) {
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

  private void givenADBIsInstalled() {
    when(adb.isInstalled()).thenReturn(true);
  }

  private void givenAnyIpToDevices() {
    when(adb.getDeviceIp((Device) anyObject())).thenReturn(ANY_DEVICE_IP);
  }

  private AndroidWiFiADB givenAnAndroidWiFiADB() {
    return new AndroidWiFiADB(adb, view);
  }
}
