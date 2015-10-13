package com.github.pedrovgs.androidwifiadb.adb;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.github.pedrovgs.androidwifiadb.Device;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import java.util.LinkedList;
import java.util.List;

public class ADB {

  private static final String ADB_PATH = "$ANDROID_HOME/platform-tools/adb";

  private final CommandLine commandLine;
  private final ADBParser adbParser;
  private final AndroidDebugBridge androidDebugBridge;

  public ADB(CommandLine commandLine, ADBParser adbParser) {
    this.commandLine = commandLine;
    this.adbParser = adbParser;
    AndroidDebugBridge.init(true);
    AndroidDebugBridge.createBridge();
    this.androidDebugBridge =  AndroidDebugBridge.getBridge();

  }

  public boolean isInstalled() {
    return androidDebugBridge.isConnected();
  }

  public List<Device> getDevicesConnectedByUSB() {
    IDevice[] adbDevices = androidDebugBridge.getDevices();
    List<Device> devices = new LinkedList<Device>();
    for (IDevice adbDevice : adbDevices) {
      String name = adbDevice.getName();
      String id = adbDevice.getAvdName();
      Device device = new Device(name, id);
      devices.add(device);
    }
    return devices;
  }

  public List<Device> connectDevices(List<Device> devices) {
    for (Device device : devices) {
      boolean connected = connectDeviceByIp(device);
      device.setConnected(connected);
    }
    return devices;
  }

  private boolean connectDeviceByIp(Device device) {
    String deviceIp = getDeviceIp(device);
    return connectDevice(deviceIp);
  }

  private String getDeviceIp(Device device) {
    String ipInfoOutput = commandLine.executeCommand(
        ADB_PATH + " -s " + device.getId() + " shell ip -f inet addr show wlan0");
    return adbParser.parseGetDeviceIp(ipInfoOutput);
  }

  private boolean connectDevice(String deviceIp) {
    commandLine.executeCommand(ADB_PATH + "tcpip 5555");
    String connectOutput = commandLine.executeCommand(ADB_PATH + "connect " + deviceIp);
    return connectOutput.contains("connected");
  }
}
