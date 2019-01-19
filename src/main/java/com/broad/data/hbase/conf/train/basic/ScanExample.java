package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;

public class ScanExample {

    public static void main(String[] args) throws IOException {


        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        helper.dropTable(TableName.valueOf("test-scan"));

        helper.createTable(TableName.valueOf("test-scan"),"row1");



    }
}
