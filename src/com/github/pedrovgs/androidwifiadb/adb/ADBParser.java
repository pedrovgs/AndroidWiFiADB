package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import java.util.LinkedList;
import java.util.List;

public class ADBParser {

  public List<Device> parseGetDevicesOutput(String adbDevicesOutput) {
    List<Device> devices = new LinkedList<Device>();
    String[] splittedOutput = adbDevicesOutput.split("\\n");
    for (String line : splittedOutput) {
      String[] deviceLine = line.split("\\t");
      if (deviceLine.length == 2) {
        String id = deviceLine[0];
        if (id.contains(".")) {
          continue;
        }
        String name = deviceLine[1];
        Device device = new Device(name, id);
        devices.add(device);
      }
    }
    return devices;
  }

  public String parseGetDeviceIp(String ipInfoOutput) {
    if (ipInfoOutput.isEmpty()) {
      return "";
    }

    String[] splittedOutput = ipInfoOutput.split("\\n");
    int end = splittedOutput[1].indexOf("/");
    int start = splittedOutput[1].indexOf("t");
    return splittedOutput[1].substring(start + 2, end);
  }
}
