package com.github.pedrovgs.androidwifiadb.view;

import com.github.pedrovgs.androidwifiadb.adb.AndroidWiFiADB;
import com.github.pedrovgs.androidwifiadb.adb.ADB;
import com.github.pedrovgs.androidwifiadb.adb.ADBParser;
import com.github.pedrovgs.androidwifiadb.adb.CommandLine;
import com.github.pedrovgs.androidwifiadb.model.Device;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Created by vgaidarji on 10/23/15.
 */
public class AndroidDevices implements ToolWindowFactory, View {

    private static final String ANDROID_WIFI_ADB_TITLE = "Android WiFi ADB";
    private static final NotificationGroup NOTIFICATION_GROUP =
            NotificationGroup.balloonGroup(ANDROID_WIFI_ADB_TITLE);

    private final AndroidWiFiADB androidWifiADB;

    private JPanel toolWindowContent;
    private JTable tableDevices;


    public AndroidDevices() {
        CommandLine commandLine = new CommandLine();
        ADBParser adbParser = new ADBParser();
        ADB adb = new ADB(commandLine, adbParser);
        this.androidWifiADB = new AndroidWiFiADB(adb, this);
    }

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);

        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                androidWifiADB.connectDevices();
                fillDevicesTable(androidWifiADB.getDevices());
            }
        });
    }

    private void fillDevicesTable(final List<Device> devices) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                AndroidDevicesTableModel model = new AndroidDevicesTableModel();
                for(Device device : devices) {
                    model.add(device);
                }
                tableDevices.setModel(model);
                ConnectDisconnectRenderer renderer = new ConnectDisconnectRenderer();
                tableDevices.getColumnModel().getColumn(2).setCellRenderer(renderer);
            }
        });
    }

    @Override public void showNoConnectedDevicesNotification() {
        showNotification(ANDROID_WIFI_ADB_TITLE,
                "There are no devices connected. Review your USB connection and try again. ",
                NotificationType.INFORMATION);
    }

    @Override public void showConnectedDeviceNotification(Device device) {
        showNotification(ANDROID_WIFI_ADB_TITLE, "Device '" + device.getName() + "' connected.",
                NotificationType.INFORMATION);
    }

    @Override public void showErrorConnectingDeviceNotification(Device device) {
        showNotification(ANDROID_WIFI_ADB_TITLE,
                "Unable to connect device '" + device.getName() + "'. Review your WiFi connection.",
                NotificationType.INFORMATION);
    }

    @Override public void showADBNotInstalledNotification() {
        showNotification(ANDROID_WIFI_ADB_TITLE,
                "'adb' command not found. Review your Android SDK installation.", NotificationType.ERROR);
    }

    private void showNotification(final String title, final String message,
                                  final NotificationType type) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override public void run() {
                Notification notification =
                        NOTIFICATION_GROUP.createNotification(title, message, type, null);
                Notifications.Bus.notify(notification);
            }
        });
    }

    public class AndroidDevicesTableModel extends AbstractTableModel {
        private List<Device> devices;

        public AndroidDevicesTableModel() {
            devices = new ArrayList<>();
        }

        @Override
        public String getColumnName(int column) {
            String value = null;
            switch (column) {
                case 0:
                    value = "Device";
                    break;
                case 1:
                    value = "State";
                    break;
                case 2:
                    value = "Action";
                    break;
            }
            return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class value = Object.class;
            switch (columnIndex) {
                case 0:
                    value = String.class;
                    break;
                case 1:
                    value = String.class;
                    break;
            }
            return value;
        }

        @Override
        public int getRowCount() {
            return devices.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Device obj = devices.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value = obj.getName();
                    break;
                case 1:
                    value = obj.isConnected() ? "connected" : "disconnected";
                    break;
                case 2:
                    break;
            }
            return value;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 2) {
                System.out.println(aValue);
                Device device = devices.get(rowIndex);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        public void add(Device value) {
            int startIndex = getRowCount();
            devices.add(value);
            fireTableRowsInserted(startIndex, getRowCount() - 1);
        }

        public void remove(Device value) {
            int startIndex = devices.indexOf(value);
            System.out.println("startIndex = " + startIndex);
            devices.remove(value);
            fireTableRowsInserted(startIndex, startIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 2;
        }
    }

    public class ConnectDisconnectPane extends JPanel {
        private JButton connect;
        private JButton disconnect;
        private String state;

        public ConnectDisconnectPane() {
            setLayout(new GridBagLayout());
            connect = new JButton("Connect");
            connect.setActionCommand("connect");
            disconnect = new JButton("Disconnect");
            disconnect.setActionCommand("disconnect");

            add(connect);
            add(disconnect);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                }
            };

            connect.addActionListener(listener);
            disconnect.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            connect.addActionListener(listener);
            disconnect.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
    }

    public class ConnectDisconnectRenderer extends DefaultTableCellRenderer {
        private ConnectDisconnectPane accconnectDisconnectPane;

        public ConnectDisconnectRenderer() {
            accconnectDisconnectPane = new ConnectDisconnectPane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                accconnectDisconnectPane.setBackground(table.getSelectionBackground());
            } else {
                accconnectDisconnectPane.setBackground(table.getBackground());
            }
            return accconnectDisconnectPane;
        }
    }

}
