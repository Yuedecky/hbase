package com.broad.data.hbase.conf.train.filter;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class ColumnPrefixFilterExample {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        helper.dropTable("testtable");
        helper.createTable(TableName.valueOf("testtable"));


        helper.fillTable("testtable",1,10,10,"colfam1","colfam2");

        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("testtable"));
        // vv ColumnPrefixFilterExample
        Filter filter = new ColumnPrefixFilter(Bytes.toBytes("col-1"));

        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        // ^^ ColumnPrefixFilterExample
        System.out.println("Results of scan:");
        // vv ColumnPrefixFilterExample
        for (Result result : scanner) {
            System.out.println(result);
        }
        scanner.close();
    }
}
