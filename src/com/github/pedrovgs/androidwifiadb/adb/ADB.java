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

  public List<Device> getDevices() {
    String adbDevicesOutput = commandLine.executeCommand("adb devices");
    return adbParser.parseGetDevicesOutput(adbDevicesOutput);
  }

  public void connectDevices(List<Device> devices) {
    for (Device device : devices) {
      connectDeviceByIp(device);
    }
  }

  private void connectDeviceByIp(Device device) {
    String deviceIp = getDeviceIp(device);
    connectDevice(deviceIp);
  }

  private String getDeviceIp(Device device) {
    String ipInfoOutput = commandLine.executeCommand(
        "adb -s " + device.getId() + " shell ip -f inet addr show wlan0");
    return adbParser.parseGetDeviceIp(ipInfoOutput);
  }

  private void connectDevice(String deviceIp) {
    commandLine.executeCommand("adb tcpip 5555");
    commandLine.executeCommand("adb connect " + deviceIp);
  }
}
