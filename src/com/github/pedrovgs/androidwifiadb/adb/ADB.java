package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import java.util.List;

public class ADB {

  private final CommandLine commandLine;
  private final ADBParser adbParser;

  public ADB(CommandLine commandLine, ADBParser adbParser) {
    this.commandLine = commandLine;
    this.adbParser = adbParser;
  }

  public boolean isInstalled() {
    return !commandLine.executeCommand("adb version").isEmpty();
  }

  public List<Device> getDevicesConnectedByUSB() {
    String adbDevicesOutput = commandLine.executeCommand("adb devices");
    return adbParser.parseGetDevicesOutput(adbDevicesOutput);
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
        "adb -s " + device.getId() + " shell ip -f inet addr show wlan0");
    return adbParser.parseGetDeviceIp(ipInfoOutput);
  }

  private boolean connectDevice(String deviceIp) {
    commandLine.executeCommand("adb tcpip 5555");
    String connectOutput = commandLine.executeCommand("adb connect " + deviceIp);
    return connectOutput.contains("connected");
  }
}
