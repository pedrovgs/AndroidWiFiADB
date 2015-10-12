package com.github.pedrovgs.androidwifiadb;

public class Device {

  private final String name;
  private final String ip;

  public Device(String name, String ip) {
    this.name = name;
    this.ip = ip;
  }

  public String getName() {
    return name;
  }

  public String getIp() {
    return ip;
  }
}
