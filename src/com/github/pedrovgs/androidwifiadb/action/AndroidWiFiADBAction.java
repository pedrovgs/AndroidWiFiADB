package com.github.pedrovgs.androidwifiadb.action;

import com.github.pedrovgs.androidwifiadb.ADB;
import com.github.pedrovgs.androidwifiadb.AndroidWiFiADB;
import com.github.pedrovgs.androidwifiadb.Device;
import com.github.pedrovgs.androidwifiadb.View;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import java.util.List;

public class AndroidWiFiADBAction extends AnAction implements View {

  private final AndroidWiFiADB androidWifiADB;

  public AndroidWiFiADBAction() {
    ADB adb = new ADB();
    this.androidWifiADB = new AndroidWiFiADB(adb, this);
  }

  public void actionPerformed(AnActionEvent event) {
    androidWifiADB.connectDevices();
  }

  @Override public void showNoConnectedDevicesNotification() {

  }

  @Override public void showConnectedDevicesNotification(List<Device> devices) {

  }
}
