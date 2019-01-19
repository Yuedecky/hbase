package com.broad.data.hbase.conf.train.filter;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class QualifierFilterExample {

    public static void main(String[] args) throws IOException {


        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        helper.dropTable("testtable");
        helper.createTable("testtable", "colfam1", "colfam2");

        System.out.println("Adding init values to testtable...");

        helper.fillTable("testtable", 1, 10, 10, "colfam1", "colfam2");

        Filter filter
                = new QualifierFilter(CompareOperator.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("col-2")));

        Scan scan = new Scan();
        scan.setFilter(filter);
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("testtable"));
        ResultScanner scanner1 = table.getScanner(scan);
        for (Result result : scanner1) {
            System.out.println(result);
        }
        scanner1.close();
        Get get = new Get(Bytes.toBytes("row-5"));

        get.setFilter(filter);
        Result res = table.get(get);

        System.out.println("Result of get {}" + res);
    }
}
