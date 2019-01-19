package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class GetExample {

    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        if(!helper.existsTable(TableName.valueOf("testtable"))){

            helper.createTable("testtable","colfam");
        }
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("testtable"));
        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"));
        Result result = table.get(get);
        byte[] val = result.getValue(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"));

        System.out.println("value:"+Bytes.toString(val));
        table.close();
        connection.close();
        helper.close();
    }
}
