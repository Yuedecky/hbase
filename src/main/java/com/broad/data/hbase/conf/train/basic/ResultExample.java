package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class ResultExample {


    public static void main(String[] args) throws IOException {


        Configuration configuration = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        helper.dropTable("person");

        helper.createTable("person", "personLocation");

        Connection connection = ConnectionFactory.createConnection(configuration);

        Table table = connection.getTable(TableName.valueOf("person"));


        Put put = new Put(Bytes.toBytes("address1"));

        put.addColumn(Bytes.toBytes("personLocation"), Bytes.toBytes("address1"), Bytes.toBytes("china"));


        put.addColumn(Bytes.toBytes("personLocation"), Bytes.toBytes("address2"), Bytes.toBytes("shanghai"));

        table.put(put);

        Get get = new Get(Bytes.toBytes("personLocation"));

        Result res1 = table.get(get);
        System.out.println(res1);


        Result res2 = Result.EMPTY_RESULT;

        System.out.println(res2);
        res2.copyFrom(res1);
        System.out.println(res2);

        table.close();
        connection.close();
        helper.close();


    }
}
