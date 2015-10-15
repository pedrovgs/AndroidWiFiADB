package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import java.util.LinkedList;
import java.util.List;

public class ADBParser {

  private static final String MODEL_INDICATOR = "model:";
  private static final String DEVICE_INDICATOR = "device:";

  public List<Device> parseGetDevicesOutput(String adbDevicesOutput) {
    List<Device> devices = new LinkedList<Device>();
    String[] splittedOutput = adbDevicesOutput.split("\\n");
    for (String line : splittedOutput) {
      String[] deviceLine = line.split("\\t");
      String id = deviceLine[0];
      if (id.contains(".") || id.contains("List of devices")) {
        continue;
      }
      int start = line.indexOf(MODEL_INDICATOR) + MODEL_INDICATOR.length();
      int end = line.indexOf(DEVICE_INDICATOR) - 1;
      if (end < 0) {
        end = line.length();
      }
      String name = line.substring(start, end);
      Device device = new Device(name, id);
      devices.add(device);
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
