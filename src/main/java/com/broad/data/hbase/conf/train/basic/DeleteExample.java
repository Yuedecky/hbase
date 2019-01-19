package com.broad.data.hbase.conf.train.basic;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class DeleteExample {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper =  HBaseHelper.getHelper(configuration);

        helper.dropTable("test-delete");

        helper.createTable(TableName.valueOf("test-delete"),100,"col-fam1","col-fam2");

        helper.put("test-delete",new String[]{
                "row1"
        },
                new String[]{
                        "col-fam1","col-fam2"
                },
        new String[]{"qual-1","qual-2","qual-3","qual2","qual3","qual2"},
                new long[]{1,2,3,4,5,6},
                new String[]{"val1","val2","val3","val1","val3","val2"});

        System.out.println("before delete call...");
        helper.dump("test-delete",new String[]{"row1"},null,null);

        Connection connection = ConnectionFactory.createConnection(configuration);

        Table table = connection.getTable(TableName.valueOf("test-delete"));

        Delete delete = new Delete(Bytes.toBytes("row1"));

        delete.setTimestamp(1);

        delete.addColumn(Bytes.toBytes("col-fam1"),Bytes.toBytes("qual-1"));
        delete.addColumn(Bytes.toBytes("col-fam2"),Bytes.toBytes("qual-3"),2);
        table.delete(delete);

        table.close();
        connection.close();

        System.out.println("After delete call...");
        helper.dump("test-delete",new String[]{"row1"},null,null);
        helper.close();


    }
}
