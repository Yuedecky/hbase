package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.metrics.ScanMetrics;

import java.io.IOException;

public class ScannCacheBatchExample {
    private static Table table = null;

    private static void scan(int caching,int batch,boolean small) throws IOException {
        int count = 0;
        Scan scan = new Scan().setCaching(caching).setBatch(batch).setSmall(small).setScanMetricsEnabled(true);

        ResultScanner scanner = table.getScanner(scan);

        for(Result result:scanner){
            count++;
        }

        scanner.close();
        ScanMetrics scanMetrics =scan.getScanMetrics();
        System.out.println("Caching:"+caching+",batch:"+batch+",Small:"+small+",Result:"+count+",RPCs:"+scanMetrics.countOfRemoteRPCcalls);


    }

    public static void main(String[] args) throws IOException {


        Configuration conf = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(conf);
        helper.dropTable("testtable");
        helper.createTable("testtable", "colfam1", "colfam2");
        helper.fillTable("testtable", 1, 10, 10,false, "colfam1", "colfam2");



        Connection connection = ConnectionFactory.createConnection(conf);
        table = connection.getTable(TableName.valueOf("testtable"));

        scan(1,1,false);

        scan(1,0,false);

        scan(5, 100, false);
        scan(10, 10, false);
        scan(2000, 100, false);


        table.close();
        connection.close();
        helper.close();


    }
}
