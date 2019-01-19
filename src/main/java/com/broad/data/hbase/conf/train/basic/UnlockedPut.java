package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class UnlockedPut implements Runnable {


    @Override
    public void run() {
        try {
//            HTable table = new HTable(HBaseConfiguration.create(), "testtable");

            Connection connection = ConnectionFactory.createConnection(HBaseConfiguration.create());
            Put put = new Put(Bytes.toBytes("ROW1"));

            put.addColumn(Bytes.toBytes("COL-FAM-1"), Bytes.toBytes("QUAL-1"), Bytes.toBytes("VAL-1"));

            long start = System.currentTimeMillis();
            System.out.println("Thread trying to put som row now...");
            connection.getTable(TableName.valueOf("test-table")).put(put);
            System.out.println("Wait time:" + (System.currentTimeMillis() - start) + "ms");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
