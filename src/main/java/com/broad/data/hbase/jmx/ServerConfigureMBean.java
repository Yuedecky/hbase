package com.broad.data.hbase.jmx;

public interface ServerConfigureMBean {

    void setPort(int port);

    int getPort();

    String getHost();

    void setHost(String newHost);
}
