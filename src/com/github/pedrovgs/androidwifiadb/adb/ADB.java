package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import java.util.LinkedList;
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

  public void connectDevices(List<Device> connectedDevices) {

  }
}
