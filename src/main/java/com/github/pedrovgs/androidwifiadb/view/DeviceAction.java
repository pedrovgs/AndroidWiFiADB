package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;

/**
 * Created by vgaidarji on 10/26/15.
 */
public interface DeviceAction {
  void connectDevice(Device device);

  void disconnectDevice(Device device);
}
