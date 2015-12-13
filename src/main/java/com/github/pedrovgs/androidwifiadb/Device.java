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

package com.github.pedrovgs.androidwifiadb;

public class Device implements Cloneable {

  private String name;
  private final String id;
  private String ip = "";
  private boolean connected;

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

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isConnected() {
    return connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(getName());
    if (!ip.isEmpty()) {
      builder.append(" (").append(ip).append(")");
    }
    return builder.toString();
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Device device = (Device) o;

    if (connected != device.connected) return false;
    if (name != null ? !name.equals(device.name) : device.name != null) return false;
    if (id != null ? !id.equals(device.id) : device.id != null) return false;
    return !(ip != null ? !ip.equals(device.ip) : device.ip != null);
  }

  @Override public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (ip != null ? ip.hashCode() : 0);
    result = 31 * result + (connected ? 1 : 0);
    return result;
  }

  @Override protected Device clone() {
    Device clone;
    try {
      clone = (Device) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
    return clone;
  }
}
