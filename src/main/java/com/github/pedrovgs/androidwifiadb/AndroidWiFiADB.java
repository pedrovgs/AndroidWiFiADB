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
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AndroidWiFiADB {

  private final ADB adb;
  private final View view;
  private final Set<Device> devices;

  public AndroidWiFiADB(ADB adb, View view) {
    this.adb = adb;
    this.view = view;
    this.devices = new LinkedHashSet<Device>();
  }

  public void connectDevices() {
    if (!isADBInstalled()) {
      view.showADBNotInstalledNotification();
      return;
    }
    devices.clear();
    devices.addAll(adb.getDevicesConnectedByUSB());
    if (devices.isEmpty()) {
      view.showNoConnectedDevicesNotification();
      return;
    }

    devices.addAll(adb.connectDevices(devices));
    showConnectionResultNotification(devices);
  }

  public boolean refreshDevicesList() {
    if (!isADBInstalled()) {
      return false;
    }
    final Collection<Device> connected = adb.getDevicesConnectedByUSB();
    for (Device connectedDevice : connected) {
      if (!checkDeviceExistance(connectedDevice)) {
        connectedDevice.setIp(adb.getDeviceIp(connectedDevice));
        devices.add(connectedDevice);
      } else {
        updateDeviceConnectionState(connectedDevice);
      }
    }
    return true;
  }

  public Collection<Device> getDevices() {
    return devices;
  }

  public void connectDevice(Device device) {
    if (!isADBInstalled()) {
      view.showADBNotInstalledNotification();
      return;
    }

    Collection<Device> connectedDevices = new ArrayList<Device>();
    connectedDevices.add(device);
    connectedDevices = adb.connectDevices(connectedDevices);
    for (Device connected : connectedDevices) {
      updateDeviceConnectionState(connected);
    }
    showConnectionResultNotification(connectedDevices);
  }

  public void disconnectDevice(Device device) {
    if (!isADBInstalled()) {
      view.showADBNotInstalledNotification();
      return;
    }

    List<Device> disconnectedDevices = new ArrayList<Device>();
    disconnectedDevices.add(device);
    disconnectedDevices = adb.disconnectDevices(disconnectedDevices);
    for (Device disconnected : disconnectedDevices) {
      updateDeviceConnectionState(disconnected);
    }
    showDisconnectionResultNotification(disconnectedDevices);
  }

  private void updateDeviceConnectionState(final Device updatedDevice) {
    devices.add(updatedDevice);
  }

  private boolean checkDeviceExistance(Device connectedDevice) {
    boolean deviceExists = false;
    for (Device device : devices) {
      if (connectedDevice.getId().equals(device.getId())) {
        deviceExists = true;
      }
    }
    return deviceExists;
  }

  private boolean isADBInstalled() {
    return adb.isInstalled();
  }

  private void showConnectionResultNotification(Collection<Device> devices) {
    for (Device device : devices) {
      if (device.isConnected()) {
        view.showConnectedDeviceNotification(device);
      } else {
        view.showErrorConnectingDeviceNotification(device);
      }
    }
  }

  private void showDisconnectionResultNotification(Collection<Device> devices) {
    for (Device device : devices) {
      if (!device.isConnected()) {
        view.showDisconnectedDeviceNotification(device);
      } else {
        view.showErrorDisconnectingDeviceNotification(device);
      }
    }
  }
}
