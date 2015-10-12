package com.github.pedrovgs.androidwifiadb.adb;

import com.github.pedrovgs.androidwifiadb.Device;
import com.github.pedrovgs.androidwifiadb.UnitTest;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ADBParserTest extends UnitTest {

  private static final String ADB_DEVICES_OUTPUT_ONE_DEVICE =
      "List of devices attached\n" + "0810a0dd00e3656f\tNexus 5";
  private static final String ADB_DEVICES_OUTPUT_SOME_DEVICES =
      "List of devices attached\n" + "0810a0dd00e3656f\tNexus 5\n" + "0810a0d333333656f\tNexus 6";
  private static final String ADB_DEVICES_NO_DEVICES = "List of devices attached\n";
  private static final String ADB_DEVICES_OUTPUT_WITH_DEVICES_BY_IP =
      "List of devices attached\n" + "0810a0dd00e3656f\tNexus 5\n" + "192.168.1.128:5555\tdevice";
  private static final String GET_IP_OUTPUT =
      "21: wlan0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP qlen 1000\n"
          + "    inet 192.168.1.128/24 brd 192.168.1.255 scope global wlan0";

  @Test
  public void shouldParseAdbDevicesOutputAndReturnTheListOfDevicesWithJustOneDeviceConnected() {
    ADBParser parser = givenAADBParser();

    List<Device> devices = parser.parseGetDevicesOutput(ADB_DEVICES_OUTPUT_ONE_DEVICE);

    assertEquals(1, devices.size());
    Device device = devices.get(0);
    assertEquals("0810a0dd00e3656f", device.getId());
    assertEquals("Nexus 5", device.getName());
  }

  @Test public void shouldParseAdbDevicesOutputAndReturnTheListOfDevicesWithMoreThanOneDevice() {
    ADBParser parser = givenAADBParser();

    List<Device> devices = parser.parseGetDevicesOutput(ADB_DEVICES_OUTPUT_SOME_DEVICES);

    assertEquals(2, devices.size());
    Device firstDevice = devices.get(0);
    Device secondDevice = devices.get(1);
    assertEquals("0810a0dd00e3656f", firstDevice.getId());
    assertEquals("Nexus 5", firstDevice.getName());
    assertEquals("0810a0d333333656f", secondDevice.getId());
    assertEquals("Nexus 6", secondDevice.getName());
  }

  @Test public void shouldReturnAnEmptyListIfThereAreNoDevices() {
    ADBParser parser = givenAADBParser();

    List<Device> devices = parser.parseGetDevicesOutput(ADB_DEVICES_NO_DEVICES);

    assertTrue(devices.isEmpty());
  }

  @Test public void shouldNotReturnDevicesConnectedByIp() {
    ADBParser parser = givenAADBParser();

    List<Device> devices = parser.parseGetDevicesOutput(ADB_DEVICES_OUTPUT_WITH_DEVICES_BY_IP);

    assertEquals(1, devices.size());
    Device device = devices.get(0);
    assertEquals("0810a0dd00e3656f", device.getId());
    assertEquals("Nexus 5", device.getName());
  }

  @Test public void shouldReturnDeviceIp() {
    ADBParser parser = givenAADBParser();

    String deviceIp = parser.parseGetDeviceIp(GET_IP_OUTPUT);

    assertEquals("192.168.1.128", deviceIp);
  }

  private ADBParser givenAADBParser() {
    return new ADBParser();
  }
}
