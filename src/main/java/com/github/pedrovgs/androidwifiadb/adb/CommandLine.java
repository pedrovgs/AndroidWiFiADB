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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

  public String executeCommand(String command) {
    Process process;
    StringBuilder stringBuilder = new StringBuilder();
    try {
      process = Runtime.getRuntime().exec(command);
      process.waitFor();
      InputStreamReader in = new InputStreamReader(process.getInputStream());
      BufferedReader reader = new BufferedReader(in);
      String line;
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line + "\n");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }
}
