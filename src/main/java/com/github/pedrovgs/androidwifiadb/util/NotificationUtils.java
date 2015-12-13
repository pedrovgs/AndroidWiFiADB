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

package com.github.pedrovgs.androidwifiadb.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;

public class NotificationUtils {

  private static final String ANDROID_WIFI_ADB_TITLE = "Android WiFi ADB";
  private static final NotificationGroup NOTIFICATION_GROUP =
      NotificationGroup.balloonGroup(ANDROID_WIFI_ADB_TITLE);

  public static void showNotification(final String message,
      final NotificationType type) {
    ApplicationManager.getApplication().invokeLater(new Runnable() {
      @Override public void run() {
        Notification notification =
            NOTIFICATION_GROUP.createNotification(ANDROID_WIFI_ADB_TITLE, message, type, null);
        Notifications.Bus.notify(notification);
      }
    });
  }
}
