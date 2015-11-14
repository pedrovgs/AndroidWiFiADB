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

import com.github.pedrovgs.androidwifiadb.model.Device;
import com.intellij.openapi.project.Project;

import org.jetbrains.android.sdk.AndroidSdkUtils;

import java.io.File;
import java.util.List;

public class ADB {

  private final CommandLine commandLine;
  private final ADBParser adbParser;
  private Project project;
  private boolean isTCPEnabled = false;

  public ADB(CommandLine commandLine, ADBParser adbParser) {
    this.commandLine = commandLine;
    this.adbParser = adbParser;
  }

  public boolean isInstalled() {
    return AndroidSdkUtils.isAndroidSdkAvailable();
  }

  public List<Device> getDevicesConnectedByUSB() {
    String getDevicesCommand = getCommand("devices -l");
    String adbDevicesOutput = commandLine.executeCommand(getDevicesCommand);
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
    String deviceIp = !device.getIp().isEmpty() ? device.getIp() : getDeviceIp(device);
    if (deviceIp.isEmpty()) {
      return false;
    } else {
      device.setIp(deviceIp);
      return connectDevice(deviceIp);
    }
  }

  public String getDeviceIp(Device device) {
    String getDeviceIpCommand =
        getCommand("-s " + device.getId() + " shell ip -f inet addr show wlan0");
    String ipInfoOutput = commandLine.executeCommand(getDeviceIpCommand);
    return adbParser.parseGetDeviceIp(ipInfoOutput);
  }

  private boolean connectDevice(String deviceIp) {
    enableTCPCommand();
    String connectDeviceCommand = getCommand("connect " + deviceIp);
    return commandLine.executeCommand(connectDeviceCommand).contains("connected");
  }

  /**
   * Disconnect connected device by IP. If device is disconnected with success, we should get an
   * empty command result.
   */
  private boolean disconnectDevice(String deviceIp) {
    enableTCPCommand();
    String connectDeviceCommand = getCommand("disconnect " + deviceIp);
    return commandLine.executeCommand(connectDeviceCommand).isEmpty();
  }

  public List<Device> disconnectDevices(List<Device> devices) {
    for (Device device : devices) {
      boolean disconnected = disconnectDevice(device.getIp());
      device.setConnected(disconnected);
    }
    return devices;
  }

  /**
   * Restarts adb in tcpip mode. Uses 5555 port.
   */
  private void enableTCPCommand() {
    if (!isTCPEnabled) {
      String enableTCPCommand = getCommand("tcpip 5555");
      commandLine.executeCommand(enableTCPCommand);
      isTCPEnabled = true;
    }
  }

  private String getAdbPath() {
    String adbPath = "";
    File adbFile = AndroidSdkUtils.getAdb(project);
    if (adbFile != null) {
      adbPath = adbFile.getAbsolutePath();
    }
    return adbPath;
  }

  private String getCommand(String command) {
    return getAdbPath() + " " + command;
  }

  public void updateProject(Project project) {
    this.project = project;
  }
}
