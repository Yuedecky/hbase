package com.broad.data.hbase.jmx;


import javax.management.AttributeChangeNotification;
import javax.management.NotificationBroadcasterSupport;
import java.util.concurrent.atomic.AtomicLong;


public class ServerConfigure extends NotificationBroadcasterSupport implements ServerConfigureMBean {


    private AtomicLong seqNumber = new AtomicLong(0);

    private int port;

    private String host;

    public ServerConfigure(int port, String host) {
        this.port = port;
        this.host = host;
    }


    @Override
    public void setHost(String newHost) {

        String oldHost = this.host;
        this.host = newHost;
        AttributeChangeNotification notification = new AttributeChangeNotification(
                this,
                seqNumber.getAndIncrement(),
                System.currentTimeMillis(),
                AttributeChangeNotification.ATTRIBUTE_CHANGE,
                "Server Host Change",
                "java.lang.String",
                oldHost,
                this.host
        );
        super.sendNotification(notification);
    }

    @Override
    public void setPort(int port) {

        int oldPort = this.port;

        this.port = port;

        AttributeChangeNotification changeNotification = new AttributeChangeNotification(
                this,
                seqNumber.getAndIncrement(),
                System.currentTimeMillis(),
                AttributeChangeNotification.ATTRIBUTE_CHANGE,
                "Server Port Change",
                "java.lang.Integer",
                oldPort,
                this.port
        );
        super.sendNotification(changeNotification);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }




}
