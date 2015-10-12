package com.github.pedrovgs.androidwifiadb;

import java.util.List;

public interface View {

  void showNoConnectedDevicesNotification();

  void showConnectedDevicesNotification(List<Device> devices);
}
