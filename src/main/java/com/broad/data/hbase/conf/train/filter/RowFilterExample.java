package com.broad.data.hbase.conf.train.filter;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class RowFilterExample {


    public static void main(String[] args) throws IOException {


        Configuration configuration = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        helper.dropTable("testtable");

        helper.createTable(TableName.valueOf("testtable"), "colfam1", "colfam2");

        System.out.println("Adding rows to table...");

        helper.fillTable("testtable", 1, 100, 100, true, "colfam1", "colfam2");

        Connection connection = ConnectionFactory.createConnection(configuration);
        //rowFilter是一个比较过滤器
        Filter filter1 = new RowFilter(CompareOperator.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row-22")));

        Scan scan = new Scan();
        scan.setFilter(filter1);
        scan.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("col-1"));
        Table table = connection.getTable(TableName.valueOf("testtable"));
        ResultScanner scanner1 = table.getScanner(scan);
        System.out.println("scanning table 1#####");
        for (Result result : scanner1) {
            System.out.println(result);
        }
        scanner1.close();

        Filter filter2 = new RowFilter(CompareOperator.EQUAL, new RegexStringComparator(".*-.5"));

        scan.setFilter(filter2);
        ResultScanner scanner2 = table.getScanner(scan);
        // ^^ RowFilterExample
        System.out.println("Scanning table #2...");
        // vv RowFilterExample
        for (Result res : scanner2) {
            System.out.println(res);
        }
        scanner2.close();


        Filter filter3 = new RowFilter(CompareOperator.EQUAL, new SubstringComparator("-5"));

        scan.setFilter(filter3);
        ResultScanner scanner3 = table.getScanner(scan);

        System.out.println("Scanning table #3...");
        for(Result result:scanner3){
            System.out.println(result);
        }
        scanner3.close();

        table.close();
        connection.close();
        helper.close();

    }
}
