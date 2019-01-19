package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class PutIdenticalExample {

    public static void main(String[] args) throws IOException {
        Configuration cf = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(cf);

        helper.dropTable("testtable");
        helper.createTable("testtable","colfam1");


        Connection connection = ConnectionFactory.createConnection(cf);
        Table table = connection.getTable(TableName.valueOf("testtable"));

        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val1"));

        table.put(put);

        Get get  = new Get(Bytes.toBytes("row1"));
        Result result = table.get(get);
        System.out.println("Result:"+result+",value:"+Bytes.toString(result.getValue(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"))));

        table.close();
        connection.close();
        helper.close();

    }
}
