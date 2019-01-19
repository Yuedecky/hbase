package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.IOException;

public class CheckAndDelete {


    public static void main(String[] args) throws IOException {
        Configuration cf = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(cf);
        helper.dropTable("test1");

        helper.createTable("test1",100,"colfam1","colfam2");

        helper.put("test1",
                new String[] { "row1" },
                new String[] { "colfam1", "colfam2" },
                new String[] { "qual1", "qual2", "qual3" },
                new long[]   { 1, 2, 3 },
                new String[] { "val1", "val2", "val3" });
        System.out.println("Before delete call...");

        helper.dump("testtable", new String[]{ "row1" }, null, null);


        helper.close();

    }
}
