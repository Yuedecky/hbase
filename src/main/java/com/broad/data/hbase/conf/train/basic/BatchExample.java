package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatchExample {
    private final static byte[] ROW1 = Bytes.toBytes("ROW1");

    private final static byte[] ROW2 = Bytes.toBytes("ROW2");

    private final static byte[] COLFAM1 = Bytes.toBytes("COL-FAM-1");

    private final static byte[] COLFAM2 = Bytes.toBytes("COL-FAM-2");

    private final static byte[] QUAL1 = Bytes.toBytes("QUAL-1");

    private final static byte[] QUAL2 = Bytes.toBytes("QUAL-2");

    public static void main(String[] args) throws IOException {


        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);
        helper.dropTable("testtable");

        helper.createTable("testtable", "COL-FAM-1", "COL-FAM-2");

        helper.put("testtable", new String[]{"ROW1"},
                new String[]{"COL-FAM-1", "COL-FAM-2"},
                new String[]{"QUAL-1", "QUAL-2"},
                new long[]{1, 2, 3, 4, 5, 6},
                new String[]{"val-1", "val-2", "val-3"});
        System.out.println("Before batch call...");
        helper.dump("testtable", new String[]{"ROW1", "ROW2"}, null, null);


        Connection connection = ConnectionFactory.createConnection(configuration);


        Table table = connection.getTable(TableName.valueOf("testtable"));
        List<Row> batch = new ArrayList<>();

        Put put = new Put(ROW1);
        put.addColumn(COLFAM1, QUAL1, Bytes.toBytes("val-5"));
        batch.add(put);


        Get get = new Get(ROW1);
        get.addColumn(COLFAM1, QUAL1);

        batch.add(get);

        Delete delete = new Delete(ROW1);
        delete.addColumn(COLFAM1, QUAL2);

        batch.add(delete);

        Object[] results = new Object[batch.size()]; // co BatchExample-6-CreateResult Create result array.

        try {
            table.batch(batch, results);
        } catch (Exception e) {

            System.err.println("batch error:" + e.getMessage());
        }

        for (int i = 0; i < results.length; i++) {
            System.out.println("Result[" + i + "]: type = " + // co BatchExample-8-Dump Print all results and class types.
                    results[i].getClass().getSimpleName() + "; " + results[i]);
        }

        // ^^ BatchExample
        table.close();
        connection.close();
        System.out.println("After batch call...");
        helper.dump("testtable", new String[]{"ROW1", "ROW2"}, null, null);
        helper.close();

    }
}
