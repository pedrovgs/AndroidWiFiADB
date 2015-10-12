package com.github.pedrovgs.androidwifiadb;

public interface View {

  void showNoConnectedDevicesNotification();

  void showConnectedDeviceNotification(Device device);

  void showErrorConnectingDeviceNotification(Device device);
}
