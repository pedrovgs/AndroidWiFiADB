package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;

public interface DeviceAction {
  void connectDevice(Device device);

  void disconnectDevice(Device device);
}
