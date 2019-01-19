package com.broad.data.hbase.conf;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * Created by yuezhy on 2018/11/1.
 */
public class HbaseConn {

    private static final HbaseConn INSTANCE = new HbaseConn();

    private static Configuration configuration;

    private static Connection connection;

    private HbaseConn() {
        try {

            if (configuration == null) {
                configuration = HBaseConfiguration.create();
                configuration.set("hbase.zookeeper.quorum", "139.196.191.239:2181");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Connection getHbaseConn() {
        return INSTANCE.getConnection();
    }

    public static Table getTable(String tabName) throws IOException {

        return INSTANCE.getConnection().getTable(TableName.valueOf(tabName));

    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
