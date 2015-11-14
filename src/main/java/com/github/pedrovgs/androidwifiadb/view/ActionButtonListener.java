package com.github.pedrovgs.androidwifiadb.view;

/**
 * Created by vgaidarji on 10/26/15.
 */
public interface ActionButtonListener {
  /**
   * @param row Row id in devices JTable
   */
  void onConnectClick(int row);

  /**
   * @param row Row id in devices JTable
   */
  void onDisconnectClick(int row);
}
