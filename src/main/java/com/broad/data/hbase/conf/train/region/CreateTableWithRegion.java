package com.broad.data.hbase.conf.train.region;

import com.broad.data.hbase.conf.train.basic.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import java.io.IOException;

public class CreateTableWithRegion {


    private static Configuration configuration = null;
    private static Connection connection = null;


    private static void printTableRegions(String tableName) throws IOException {
        System.out.println("Printing regions of table:" + tableName);
        TableName tab = TableName.valueOf(tableName);
        RegionLocator locator = connection.getRegionLocator(tab);
        Pair<byte[][], byte[][]> pair = locator.getStartEndKeys();
        for (int n = 0; n < pair.getFirst().length; n++) {

            byte[] sk = pair.getFirst()[n];
            byte[] ek = pair.getSecond()[n];
            System.out.println("[" + (n + 1) + "]" +
                    " start key: " +
                    (sk.length == 8 ? Bytes.toLong(sk) : Bytes.toStringBinary(sk)) + // co CreateTableWithRegionsExample-3-Print Print the key, but guarding against the empty start (and end) key.
                    ", end key: " +
                    (ek.length == 8 ? Bytes.toLong(ek) : Bytes.toStringBinary(ek)));
        }
        locator.close();
    }

    public static void main(String[] args) throws IOException {
        configuration = HBaseConfiguration.create();
        connection= ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        HBaseHelper helper = HBaseHelper.getHelper(configuration);
        String regionTable = "region-table";
        String regionTable2 = "region-table-2";
        helper.dropTable(regionTable);
        helper.dropTable(regionTable2);
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(regionTable));
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(Bytes.toBytes("cf"));
        descriptor.addFamily(columnDescriptor);
        admin.createTable(descriptor,Bytes.toBytes(1L),Bytes.toBytes(100L),10);
        printTableRegions(regionTable);

        byte[][] regions = new byte[][]{
                Bytes.toBytes("A"),
                Bytes.toBytes("B"),
                Bytes.toBytes("G"),
                Bytes.toBytes("K"),
                Bytes.toBytes("O"),
                Bytes.toBytes("Y")
        };
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(regionTable2));
        hTableDescriptor.addFamily(columnDescriptor);
        admin.createTable(hTableDescriptor,regions);

        printTableRegions(regionTable2);

    }
}
