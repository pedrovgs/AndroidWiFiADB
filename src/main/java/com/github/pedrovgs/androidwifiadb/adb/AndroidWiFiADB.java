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

import java.util.List;

public class AndroidWiFiADB {

  private final ADB adb;
  private final View view;
  private List<Device> devices;

  public AndroidWiFiADB(ADB adb, View view) {
    this.adb = adb;
    this.view = view;
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
    for (Device device : devices) {
      if (device.isConnected()) {
        view.showConnectedDeviceNotification(device);
      } else {
        view.showErrorConnectingDeviceNotification(device);
      }
    }
  }

  public List<Device> getDevices() {
    return devices;
  }

  private boolean isADBInstalled() {
    return adb.isInstalled();
  }

  public void updateProject(Project project) {
    this.adb.updateProject(project);
  }
}
