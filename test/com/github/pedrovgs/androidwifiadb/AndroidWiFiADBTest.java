package com.github.pedrovgs.androidwifiadb;

import com.github.pedrovgs.androidwifiadb.adb.ADB;
import java.util.LinkedList;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AndroidWiFiADBTest extends UnitTest {

  @Mock private ADB adb;
  @Mock private View view;

  @Test public void shouldShowErrorIfADBIsNotInstalled() {
    AndroidWiFiADB sut = givenAnAndroidWiFiADB();
    givenADBIsNotInstalled();

    sut.connectDevices();

    verify(view).showADBNotInstalledNotification();
  }

  @Test public void shouldShowNoConnectedDevicesNotificationIfThereAreNotConnectedDevicesByUSB() {
    AndroidWiFiADB sut = givenAnAndroidWiFiADB();
    givenThereAreNoConnectedDevices();

    sut.connectDevices();

    verify(view).showNoConnectedDevicesNotification();
  }

  private void givenThereAreNoConnectedDevices() {
    when(adb.isInstalled()).thenReturn(true);
    when(adb.getDevicesConnectedByUSB()).thenReturn(new LinkedList<Device>());
  }

  private void givenADBIsNotInstalled() {
    when(adb.isInstalled()).thenReturn(false);
  }

  private AndroidWiFiADB givenAnAndroidWiFiADB() {
    return new AndroidWiFiADB(adb, view);
  }
}
