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
import java.util.LinkedList;
import java.util.List;

public class ADBParser {

  private static final String MODEL_INDICATOR = "model:";
  private static final String DEVICE_INDICATOR = "device:";
  private static final String IP_SEPARATOR = ".";
  private static final String END_DEVICE_IP_INDICATOR = "/";
  private static final String START_DEVICE_IP_INDICATOR = "t";

  public List<Device> parseGetDevicesOutput(String adbDevicesOutput) {
    List<Device> devices = new LinkedList<Device>();
    String[] splittedOutput = adbDevicesOutput.split("\\n");
    if (splittedOutput.length == 1) {
      return devices;
    }
    for (int i = 1; i < splittedOutput.length; i++) {
      String line = splittedOutput[i];
      String[] deviceLine = line.split("\\t");
      String id = deviceLine[0].substring(0, deviceLine[0].indexOf(" "));
      if (id.contains(IP_SEPARATOR)) {
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
    int end = splittedOutput[1].indexOf(END_DEVICE_IP_INDICATOR);
    int start = splittedOutput[1].indexOf(START_DEVICE_IP_INDICATOR);
    return splittedOutput[1].substring(start + 2, end);
  }
}
