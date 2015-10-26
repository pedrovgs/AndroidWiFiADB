package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.model.Device;

/**
 * Created by vgaidarji on 10/26/15.
 */
public interface DeviceStateListener {
    void onDeviceConnected(Device device);
    void onDeviceDisconnected(Device device);
}
