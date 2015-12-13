/*
 * Copyright (C) 2015 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import com.intellij.util.EnvironmentUtil;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class ADB {

  private static final String ANDROID_ENV_VAR_NAME = "ANDROID_HOME";
  private static final String ADB_RELATIVE_PATH =
      File.separator + "platform-tools" + File.separator + "adb";
  private static final String TCPIP_PORT = "5555";

  private final CommandLine commandLine;
  private final ADBParser adbParser;

  public ADB(CommandLine commandLine, ADBParser adbParser) {
    this.commandLine = commandLine;
    this.adbParser = adbParser;
  }

  public boolean isInstalled() {
    String versionCommand = getCommand("version");
    return !commandLine.executeCommand(versionCommand).isEmpty();
  }

  public Collection<Device> getDevicesConnectedByUSB() {
    String getDevicesCommand = getCommand("devices -l");
    String adbDevicesOutput = commandLine.executeCommand(getDevicesCommand);
    return adbParser.parseGetDevicesOutput(adbDevicesOutput);
  }

  public Collection<Device> connectDevices(Collection<Device> devices) {
    for (Device device : devices) {
      boolean connected = connectDeviceByIp(device);
      device.setConnected(connected);
    }
    return devices;
  }

  public String getDeviceIp(Device device) {
    String getDeviceIpCommand =
        getCommand("-s " + device.getId() + " shell ip -f inet addr show wlan0");
    String ipInfoOutput = commandLine.executeCommand(getDeviceIpCommand);
    return adbParser.parseGetDeviceIp(ipInfoOutput);
  }

  public List<Device> disconnectDevices(List<Device> devices) {
    for (Device device : devices) {
      boolean disconnected = disconnectDevice(device.getIp());
      device.setConnected(disconnected);
    }
    return devices;
  }

  private boolean connectDeviceByIp(Device device) {
    String deviceIp = !device.getIp().isEmpty() ? device.getIp() : getDeviceIp(device);
    if (deviceIp.isEmpty()) {
      return false;
    } else {
      device.setIp(deviceIp);
      return connectDevice(deviceIp);
    }
  }

  private boolean connectDevice(String deviceIp) {
    enableTCPCommand();
    String connectDeviceCommand = getCommand("connect " + deviceIp);
    return commandLine.executeCommand(connectDeviceCommand).contains("connected");
  }

  private boolean disconnectDevice(String deviceIp) {
    enableTCPCommand();
    String connectDeviceCommand = getCommand("disconnect " + deviceIp);
    return commandLine.executeCommand(connectDeviceCommand).isEmpty();
  }

  private void enableTCPCommand() {
    if (!checkTCPCommandExecuted()) {
      String enableTCPCommand = getCommand("tcpip " + TCPIP_PORT);
      commandLine.executeCommand(enableTCPCommand);
    }
  }

  private boolean checkTCPCommandExecuted() {
    String getPropCommand = getCommand("adb shell getprop | grep adb");
    String getPropOutput = commandLine.executeCommand(getPropCommand);
    String adbTcpPort = adbParser.parseAdbServiceTcpPort(getPropOutput);
    return TCPIP_PORT.equals(adbTcpPort);
  }

  private String getAdbPath() {
    String androidSdkPath = EnvironmentUtil.getValue(ANDROID_ENV_VAR_NAME);
    if (androidSdkPath == null || androidSdkPath.isEmpty()) {
      return "";
    }
    String adbPath = ADB_RELATIVE_PATH;
    String separator =
        androidSdkPath.substring(androidSdkPath.length() - 1).equals(File.separator) ? ""
            : File.separator;
    return androidSdkPath + separator + adbPath;
  }

  private String getCommand(String command) {
    return getAdbPath() + " " + command;
  }
}
