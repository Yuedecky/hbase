package com.broad.data.hbase.conf.train.filter;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 如果用户需要返回一行中的第一列，则这种过滤器可以满足需要
 * 这种过滤器通常在行数统计的应用场景中使用
 */
public class FirstKeyOnlyFilterExample {

    public static void main(String[] args) throws IOException {


        Configuration configuration = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        helper.dropTable("testtable");

        helper.createTable(TableName.valueOf("testable"),"colfam1","colfam2");

        System.out.println("adding rows  to table");

        helper.fillTableRandom("testtable",1,10,0,1,30,0,0,100,0,true,"colfam1");


        Connection connection = ConnectionFactory.createConnection(configuration);

        Table table = connection.getTable(TableName.valueOf("testtable"));

        Filter filter = new FirstKeyOnlyFilter();
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);

        System.out.println("Result of scan:");

        int rowCount = 0;
        for(Result result:scanner){
            for(Cell cell:result.rawCells()){
                System.out.println("Cell: " + cell + ", Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));

            }
            rowCount++;
        }
        System.out.println("Total num of rows:"+rowCount);

        scanner.close();
    }
}
