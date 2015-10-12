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
