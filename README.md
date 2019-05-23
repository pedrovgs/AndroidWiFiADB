Android WiFi ADB - IntelliJ/Android Studio Plugin [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20WiFi%20ADB-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2654)
=================================================
![Android WiFi ADB][1]

**IntelliJ and Android Studio plugin** created to **quickly connect your Android device over WiFi** to install, run and debug your applications without a USB connected. Press one button and forget about your USB cable.

**Android WiFI ADB plugin adds a button ![Android WiFi ADB Button][5] to your IntelliJ/Android Studio Toolbar** to connect your device to your computer over WiFi.  

*To use this plugin the project opened in your IntelliJ/Android Studio has to be an Android project configured with the Android SDK.*

Screenshots
-----------
Connect all devices button:

![Android WiFi ADB Usage][2]

Devices dashboard:

![Android Devices Window][7]

Usage
-----

Connect your device to your computer using a USB cable. Then press the button, and a notification will pop up saying that the phone has been connected. Disconnect your USB once the plugin shows your device is connected. Open the Android WiFi ADB tab at the right side to see all the devices and manage your connections. Your device will be connected over WiFi now. You can now deploy, run and debug your device using your WiFi connection. Remember that your device and your computer have to be in the same WiFi connection. Also, you have to first connect your device with a USB every time you open Android Studio, for setting up the connection over WiFi.
  
If you want to handle your devices connection individually, open the Android WiFi ADB dashboard you will find at the right of your IDE.  

Installation
------------

Download and install **Android WiFi ADB** directly from Intellij / Android Studio:
`Preferences/Settings->Plugins->Browse Repositories` 

Alternatively, you can [download the plugin][6] from the JetBrains plugin site and install it manually in:
`Preferences/Settings->Plugins->Install plugin from disk`.

Build the project
-----------------

If you need some information about how to build this project review [IntelliJ Idea's Gradle Plugin documentation](https://github.com/JetBrains/gradle-intellij-plugin).

Do you want to contribute?
--------------------------

Please, do it! If you have any improvement or you've found any bug, send a pull request with the code or open an issue :)

Libraries used on the sample project
------------------------------------

* [JUnit][3]
* [Mockito][4]

Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2015 Pedro Vicente Gómez Sánchez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: ./art/AndroidWiFiADBIcon.png
[2]: ./art/screenshot1.gif
[3]: https://github.com/junit-team/junit
[4]: https://github.com/mockito/mockito
[5]: ./art/sampleButton.png
[6]: https://plugins.jetbrains.com/plugin/7983
[7]: ./art/android_devices_window.png
