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

package com.github.pedrovgs.androidwifiadb.adb;

import com.intellij.openapi.project.Project;
import com.github.pedrovgs.androidwifiadb.view.View;
import com.github.pedrovgs.androidwifiadb.model.Device;

import java.util.ArrayList;
import java.util.List;

public class AndroidWiFiADB {

  private final ADB adb;
  private final View view;
  private List<Device> devices;

  public AndroidWiFiADB(ADB adb, View view) {
    this.adb = adb;
    this.view = view;
    this.devices = new ArrayList<Device>();
  }

  public void connectDevices() {
    if (!isADBInstalled()) {
      view.showADBNotInstalledNotification();
      return;
    }
    devices = adb.getDevicesConnectedByUSB();
    if (devices.isEmpty()) {
      view.showNoConnectedDevicesNotification();
      return;
    }

    devices = adb.connectDevices(devices);
    showConnectionResultNotification(devices);
  }

  public boolean refreshDevicesList() {
    if (!isADBInstalled()) {
      return false;
    }
    List<Device> connected = adb.getDevicesConnectedByUSB();
    for (Device connectedDevice : connected) {
      boolean deviceExists = checkDeviceExistance(connectedDevice);
      if (!deviceExists) {
        connectedDevice.setIp(adb.getDeviceIp(connectedDevice));
        devices.add(connectedDevice);
      } else {
        updateDeviceConnectionState(connectedDevice);
      }
    }
    return true;
  }

  public List<Device> getDevices() {
    return devices;
  }

  public void updateProject(Project project) {
    this.adb.updateProject(project);
  }

  public void connectDevice(Device device) {
    if (!isADBInstalled()) {
      view.showADBNotInstalledNotification();
      return;
    }

    List<Device> connectedDevices = new ArrayList<Device>();
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
    for (Device d : disconnectedDevices) {
      updateDeviceConnectionState(d);
    }
    showDisconnectionResultNotification(disconnectedDevices);
  }

  private void updateDeviceConnectionState(Device updatedDevice) {
    for (int i = 0, size = devices.size(); i < size; i++) {
      Device device = devices.get(i);
      if (updatedDevice.getId().equals(device.getId())) {
        devices.remove(i);
        device.setName(updatedDevice.getName());
        devices.add(i, device);
      }
    }
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

  private void showConnectionResultNotification(List<Device> devices) {
    for (Device device : devices) {
      if (device.isConnected()) {
        view.showConnectedDeviceNotification(device);
      } else {
        view.showErrorConnectingDeviceNotification(device);
      }
    }
  }

  private void showDisconnectionResultNotification(List<Device> devices) {
    for (Device device : devices) {
      if (!device.isConnected()) {
        view.showDisconnectedDeviceNotification(device);
      } else {
        view.showErrorDisconnectingDeviceNotification(device);
      }
    }
  }
}
