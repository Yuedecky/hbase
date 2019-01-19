package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.IOException;

public class GetFluentExample {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(configuration);


        helper.dropTable("testtable");
        helper.createTable("testtable","colfam");

        helper.put("testtable",
                new String[] { "row1" },
                new String[] { "colfam1", "colfam2" },
                new String[] { "qual1", "qual1", "qual2", "qual2" },
                new long[]   { 1, 2, 3, 4 },
                new String[] { "val1", "val1", "val2", "val2" });

        
    }
}
