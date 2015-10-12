package com.github.pedrovgs.androidwifiadb;

import java.util.List;

public class AndroidWiFiADB {

  private final ADB adb;
  private final View view;

  public AndroidWiFiADB(ADB adb, View view) {
    this.adb = adb;
    this.view = view;
  }

  public void connectDevices() {
    List<Device> devices = adb.getDevices();
    if (devices.isEmpty()) {
      view.showNoConnectedDevicesNotification();
    }else{
      adb.connectDevices(devices);
      view.showConnectedDevicesNotification(devices);
    }
  }
}
