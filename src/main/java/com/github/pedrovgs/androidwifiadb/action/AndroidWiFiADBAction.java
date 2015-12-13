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

package com.github.pedrovgs.androidwifiadb.action;

import com.github.pedrovgs.androidwifiadb.AndroidWiFiADB;
import com.github.pedrovgs.androidwifiadb.view.NotificationView;
import com.github.pedrovgs.androidwifiadb.adb.ADB;
import com.github.pedrovgs.androidwifiadb.adb.ADBParser;
import com.github.pedrovgs.androidwifiadb.adb.CommandLine;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;

public class AndroidWiFiADBAction extends AnAction {

  private final AndroidWiFiADB androidWifiADB;

  public AndroidWiFiADBAction() {
    CommandLine commandLine = new CommandLine();
    ADBParser adbParser = new ADBParser();
    ADB adb = new ADB(commandLine, adbParser);
    this.androidWifiADB = new AndroidWiFiADB(adb, new NotificationView());
  }

  public void actionPerformed(final AnActionEvent event) {
    ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
      public void run() {
        androidWifiADB.connectDevices();
      }
    });
  }
}
