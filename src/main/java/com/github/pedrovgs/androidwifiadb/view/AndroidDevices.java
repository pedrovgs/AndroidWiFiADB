package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.adb.ADB;
import com.github.pedrovgs.androidwifiadb.adb.ADBParser;
import com.github.pedrovgs.androidwifiadb.adb.AndroidWiFiADB;
import com.github.pedrovgs.androidwifiadb.adb.CommandLine;
import com.github.pedrovgs.androidwifiadb.model.Device;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.JPanel;

/**
 * Created by vgaidarji on 10/23/15.
 */
public class AndroidDevices implements ToolWindowFactory, View, DeviceAction {

    private int INTERVAL_REFRESH_DEVICES = 1000;
    private static final String ANDROID_WIFI_ADB_TITLE = "Android WiFi ADB";
    private static final NotificationGroup NOTIFICATION_GROUP =
            NotificationGroup.balloonGroup(ANDROID_WIFI_ADB_TITLE);

    private final AndroidWiFiADB androidWifiADB;
    private JPanel toolWindowContent;
    private final CardLayoutDevices cardLayoutDevices;

    public AndroidDevices() {
        CommandLine commandLine = new CommandLine();
        ADBParser adbParser = new ADBParser();
        ADB adb = new ADB(commandLine, adbParser);
        this.androidWifiADB = new AndroidWiFiADB(adb, this);
        cardLayoutDevices = new CardLayoutDevices(toolWindowContent, this);
    }

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);

        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                setupUI();
                monitorDevices();
            }
        });
    }

    private void setupUI() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                cardLayoutDevices.createAndShowGUI();
            }
        });
    }

    /**
     * Monitors connected devices state and triggers UI updates.
     */
    private void monitorDevices() {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                while (true) {
                    boolean refreshRequired = androidWifiADB.refreshDevicesList();
                    if (refreshRequired) {
                        updateUi();
                    }

                    try {
                        Thread.sleep(INTERVAL_REFRESH_DEVICES);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void showNoConnectedDevicesNotification() {
        showNotification(ANDROID_WIFI_ADB_TITLE,
                "There are no devices connected. Review your USB connection and try again. ",
                NotificationType.INFORMATION);
    }

    @Override
    public void showConnectedDeviceNotification(Device device) {
        showNotification(ANDROID_WIFI_ADB_TITLE, "Device '" + device.getName() + "' connected.",
                NotificationType.INFORMATION);
    }

    @Override
    public void showDisconnectedDeviceNotification(Device device) {
        showNotification(ANDROID_WIFI_ADB_TITLE, "Device '" + device.getName() + "' disconnected.",
                NotificationType.INFORMATION);
    }

    @Override
    public void showErrorConnectingDeviceNotification(Device device) {
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

    @Override
    public void showADBNotInstalledNotification() {
        showNotification(ANDROID_WIFI_ADB_TITLE,
                "'adb' command not found. Review your Android SDK installation.", NotificationType.ERROR);
    }

    private void showNotification(final String title, final String message,
                                  final NotificationType type) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification =
                        NOTIFICATION_GROUP.createNotification(title, message, type, null);
                Notifications.Bus.notify(notification);
            }
        });
    }

    @Override
    public void connectDevice(final Device device) {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                androidWifiADB.connectDevice(device);
                updateUi();
            }
        });
    }

    @Override
    public void disconnectDevice(final Device device) {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                androidWifiADB.disconnectDevice(device);
                updateUi();
            }
        });
    }

    private void updateUi() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                cardLayoutDevices.setDevices(androidWifiADB.getDevices());
                cardLayoutDevices.updateUi();
            }
        });
    }
}
