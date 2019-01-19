package com.broad.data.hbase.jmx;


import javax.management.Notification;
import javax.management.NotificationListener;

public class ServerConfigureChangedListener implements NotificationListener {


    @Override
    public void handleNotification(Notification notification, Object handback) {

        //todo 如何解析变化的配置信息
        System.out.println("---i have listened configure changed---");
        if (handback instanceof ServerConfigure) {
            ServerConfigure configure = (ServerConfigure) handback;
            System.out.println("new port:" + configure.getPort());
            System.out.println("new host:" + configure.getHost());
        }
    }
}
