package com.github.pedrovgs.androidwifiadb;

import com.github.pedrovgs.androidwifiadb.action.AndroidWiFiADB;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class AndroidWiFiADBAction extends AnAction {

  private final AndroidWiFiADB androidWifiADB;

  public AndroidWiFiADBAction() {
    this.androidWifiADB = new AndroidWiFiADB();
  }

  public void actionPerformed(AnActionEvent event) {
    androidWifiADB.connectDevices();
  }
}
