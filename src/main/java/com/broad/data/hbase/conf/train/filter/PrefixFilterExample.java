package com.broad.data.hbase.conf.train.filter;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class PrefixFilterExample {

    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);


        helper.dropTable("testtable");
        helper.createTable(TableName.valueOf("testtable"), "colfam1", "colfam2");

        System.out.println("Adding init table values");
        helper.fillTable("testtable", 1, 100, 10, "colfam1", "colfam2");

        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("testtable"));

        Filter prefixFilter = new PrefixFilter(Bytes.toBytes("row-1"));

        Scan scan = new Scan();
        scan.setFilter(prefixFilter);
        ResultScanner scanner1 = table.getScanner(scan);
        System.out.println("start scanning #1");
        for (Result result : scanner1) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell:" + cell + ",value:" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
        }

        scanner1.close();
        Get get = new Get(Bytes.toBytes("row-5"));

        get.setFilter(prefixFilter);

        Result result = table.get(get);

        System.out.println("starting scanning #2...");
        for(Cell cell:result.rawCells()){
            System.out.println("Cell: " + cell + ", Value: " +
                    Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                            cell.getValueLength()));
        }
    }
}
