package com.github.pedrovgs.androidwifiadb;

public class Device {

  private final String name;
  private final String id;

  public Device(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }
}
