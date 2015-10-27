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

package com.github.pedrovgs.androidwifiadb.action;

import com.github.pedrovgs.androidwifiadb.adb.AndroidWiFiADB;
import com.github.pedrovgs.androidwifiadb.model.Device;
import com.github.pedrovgs.androidwifiadb.view.View;
import com.github.pedrovgs.androidwifiadb.adb.ADB;
import com.github.pedrovgs.androidwifiadb.adb.ADBParser;
import com.github.pedrovgs.androidwifiadb.adb.CommandLine;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;

public class AndroidWiFiADBAction extends AnAction implements View {

  private static final String ANDROID_WIFI_ADB_TITLE = "Android WiFi ADB";
  private static final NotificationGroup NOTIFICATION_GROUP =
      NotificationGroup.balloonGroup(ANDROID_WIFI_ADB_TITLE);

  private final AndroidWiFiADB androidWifiADB;

  public AndroidWiFiADBAction() {
    CommandLine commandLine = new CommandLine();
    ADBParser adbParser = new ADBParser();
    ADB adb = new ADB(commandLine, adbParser);
    this.androidWifiADB = new AndroidWiFiADB(adb, this);
  }

  public void actionPerformed(final AnActionEvent event) {
    this.androidWifiADB.updateProject(event.getProject());
    ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
      public void run() {
        androidWifiADB.connectDevices();
      }
    });
  }

  @Override public void showNoConnectedDevicesNotification() {
    showNotification(ANDROID_WIFI_ADB_TITLE,
        "There are no devices connected. Review your USB connection and try again. ",
        NotificationType.INFORMATION);
  }

  @Override public void showConnectedDeviceNotification(Device device) {
    showNotification(ANDROID_WIFI_ADB_TITLE, "Device '" + device.getName() + "' connected.",
        NotificationType.INFORMATION);
  }

    @Override
    public void showDisconnectedDeviceNotification(Device device) {
        showNotification(ANDROID_WIFI_ADB_TITLE, "Device '" + device.getName() + "' disconnected.",
                NotificationType.INFORMATION);
    }

    @Override public void showErrorConnectingDeviceNotification(Device device) {
    showNotification(ANDROID_WIFI_ADB_TITLE,
        "Unable to connect device '" + device.getName() + "'. Review your WiFi connection.",
        NotificationType.INFORMATION);
  }

    @Override
    public void showErrorDisconnectingDeviceNotification(Device device) {
        showNotification(ANDROID_WIFI_ADB_TITLE,
                "Unable to disconnect device '" + device.getName() + "'. Review your WiFi connection.",
                NotificationType.INFORMATION);
    }

    @Override public void showADBNotInstalledNotification() {
    showNotification(ANDROID_WIFI_ADB_TITLE,
        "Android SDK not found. Please, review your project configuration and ensure that you are working on an "
                + "Android project.", NotificationType.ERROR);
  }

  private void showNotification(final String title, final String message,
      final NotificationType type) {
    ApplicationManager.getApplication().invokeLater(new Runnable() {
      @Override public void run() {
        Notification notification =
            NOTIFICATION_GROUP.createNotification(title, message, type, null);
        Notifications.Bus.notify(notification);
      }
    });
  }
}
