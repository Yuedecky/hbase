package com.broad.data.hbase.conf.train.filter;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class KeyOnlyFilterExample {


    private static void scan(Filter filter, Table table) throws IOException {
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner results = table.getScanner(scan);
        System.out.println("Result of scan...");
        int rowCount = 0;
        for (Result result : results) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell:" + cell + ",value=" + (cell.getValueLength() > 0 ? Bytes.toInt(cell
                        .getValueArray(), cell.getValueOffset(), cell.getValueLength()) :
                        "n/a"));
            }
            rowCount++;
        }
        System.out.println("Total of rows:" + rowCount);
        results.close();
    }

    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        HBaseHelper hBaseHelper = HBaseHelper.getHelper(configuration);
        String keyOnlyFilterTable = "key-only-filter-table";
        hBaseHelper.dropTable(keyOnlyFilterTable);
        System.setProperty("HADOOP_HOME", "/opt/hadoop");
        hBaseHelper.createTable(keyOnlyFilterTable, "col-fam1");
        System.out.println("Adding rows to table: " + keyOnlyFilterTable);
        hBaseHelper.fillTableRandom(keyOnlyFilterTable,
                1, 5, 0, 1, 30, 0, 0, 10000, 0,
                true, "col-fam1");

        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf(keyOnlyFilterTable));
        System.out.println("scan table #1");
        Filter filter = new KeyOnlyFilter();
        scan(filter, table);
        System.out.println("scan table #2");
        Filter filter1 = new KeyOnlyFilter(true);
        scan(filter1, table);

    }
}
