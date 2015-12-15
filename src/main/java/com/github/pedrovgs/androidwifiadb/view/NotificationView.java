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

import com.github.pedrovgs.androidwifiadb.Device;
import com.intellij.notification.NotificationType;

import static com.github.pedrovgs.androidwifiadb.util.NotificationUtils.showNotification;

public class NotificationView implements View {
  @Override public void showNoConnectedDevicesNotification() {
    showNotification("There are no devices connected. Review your USB connection and try again.",
        NotificationType.INFORMATION);
  }

  @Override public void showConnectedDeviceNotification(Device device) {
    showNotification("Device '" + device.getName() + "' connected.",
        NotificationType.INFORMATION);
  }

  @Override public void showDisconnectedDeviceNotification(Device device) {
    showNotification("Device '" + device.getName() + "' disconnected.",
        NotificationType.INFORMATION);
  }

  @Override public void showErrorConnectingDeviceNotification(Device device) {
    showNotification(
        "Unable to connect to device '" + device.getName() + "'. Make sure that your computer and your "
        + "device are connected to the same WiFi network.",
        NotificationType.INFORMATION);
  }

  @Override public void showErrorDisconnectingDeviceNotification(Device device) {
    showNotification(
        "Unable to disconnect device '" + device.getName() + "'. Make sure that your computer and your "
        + "device are connected to the same WiFi network.",
        NotificationType.INFORMATION);
  }

  @Override public void showADBNotInstalledNotification() {
    showNotification("'adb' command not found. Review your Android SDK installation.",
        NotificationType.ERROR);
  }
}
