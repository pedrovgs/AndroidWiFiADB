package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import java.util.LinkedList;
import java.util.List;

public class ADBParser {
  public List<Device> parseGetDevicesOutput(String adbDevicesOutput) {
    List<Device> devices = new LinkedList<>();
    String[] splittedOutput = adbDevicesOutput.split("\\n");
    for (String line : splittedOutput) {
      String[] deviceLine = line.split("\\t");
      if(deviceLine.length == 2){
        String id = deviceLine[0];
        String name = deviceLine[1];
        Device device = new Device(name, id);
        devices.add(device);
      }
    }
    return devices;
  }
}
