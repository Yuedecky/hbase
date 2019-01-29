package com.broad.data.hbase.conf.train.rest;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.rest.client.Client;
import org.apache.hadoop.hbase.rest.client.Cluster;
import org.apache.hadoop.hbase.rest.client.RemoteHTable;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseRestOp {

    public static void main(String[] args) throws IOException {
        System.setProperty("HADOOP_HOME", "/opt/hadoop");
        Cluster cluster = new Cluster();
        cluster.add("localhost", 8080);
        Client client = new Client(cluster);
        RemoteHTable table = new RemoteHTable(client, "region-table");
        Get get = new Get(Bytes.toBytes("row-1"));
        get.addColumn(Bytes.toBytes("cf"), Bytes.toBytes(""));
        Result result = table.get(get);
        System.err.println("Get result:" + result);
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes("row-1"));
        scan.setStopRow(Bytes.toBytes("row-2"));
        scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes(""));
        ResultScanner scanner = table.getScanner(scan);
        for (Result ret : scanner) {
            System.out.println("Scan row[" + Bytes.toString(ret.getRow()) + "]:" + ret);
        }

    }
}
