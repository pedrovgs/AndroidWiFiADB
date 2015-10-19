Android WiFi ADB - IntelliJ/Android Studio Plugin [![Build Status](https://travis-ci.org/pedrovgs/AndroidWiFiADB.svg?branch=master)](https://travis-ci.org/pedrovgs/AndroidWiFiADB) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20WiFi%20ADB-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2654)
=================================================
![Android WiFi ADB][1]

**IntelliJ and Android Studio plugin** created to **quickly connect your Android device over WiFi** to install, run and debug your applications without a USB connected. Press one button and forget about your USB cable.

**Android WiFI ADB plugin adds a button ![Android WiFi ADB Button][5] to your IntelliJ/Android Studio Toolbar** to connect your device to your computer over WiFi.  

*To use this plugin you have to declare the ``ANDROID_HOME`` env var properly.*

Screenshots
-----------

![Android WiFi ADB Usage][2]

Installation
------------

Download and install **Android WiFi ADB** directly from Intellij / Android Studio:
`Preferences/Settings->Plugins->Browse Repositories` 

Alternatively, you can [download the plugin][6] from the JetBrains plugin site and install it manually in:
`Preferences/Settings->Plugins->Install plugin from disk`.

**The plugin is waiting for approval by the IntelliJ Plugin Store developers** If you don't find the plugin the IntelliJ plugin store you can [download here][6].

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
  <img alt="Follow me on Twitter" src="http://imageshack.us/a/img812/3923/smallth.png" />
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
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
[6]: https://drive.google.com/file/d/0B9xkpTnF9BXjdHExMXdvUGJMb0E/view?usp=sharing
