package com.github.pedrovgs.androidwifiadb.action;

import com.android.ddmlib.AndroidDebugBridge;
import com.github.pedrovgs.androidwifiadb.AndroidWiFiADB;
import com.github.pedrovgs.androidwifiadb.Device;
import com.github.pedrovgs.androidwifiadb.View;
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
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.impl.SdkFinder;

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

  public void actionPerformed(AnActionEvent event) {
    new Thread(new Runnable() {
      @Override public void run() {
        androidWifiADB.connectDevices();
      }
    }).start();

  }

  @Override public void showNoConnectedDevicesNotification() {
    showNotification(ANDROID_WIFI_ADB_TITLE, "There are no devices connected with a USB cable.",
        NotificationType.INFORMATION);
  }

  @Override public void showConnectedDeviceNotification(Device device) {
    showNotification(ANDROID_WIFI_ADB_TITLE, "Device '" + device.getName() + "' connected.",
        NotificationType.INFORMATION);
  }

  @Override public void showErrorConnectingDeviceNotification(Device device) {
    showNotification(ANDROID_WIFI_ADB_TITLE, "Unable to connect device '" + device.getName() + "'.",
        NotificationType.INFORMATION);
  }

  @Override public void showADBNotInstalledNotification() {
    showNotification(ANDROID_WIFI_ADB_TITLE,
        "'adb' command not found. Review your Android SDK installation.", NotificationType.ERROR);
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
