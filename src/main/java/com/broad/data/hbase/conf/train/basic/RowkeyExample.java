package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.hbase.client.Put;

import java.util.Arrays;

public class RowkeyExample {

    public static void main(String[] args) throws Exception {

        byte[] data = new byte[100];

        Arrays.fill(data, (byte) '#');


        String username = "tome";

        byte[] usernameBytes = username.getBytes("UTF-8");
        System.arraycopy(usernameBytes, 0, data, 45, usernameBytes.length);

        Put put = new Put(data, 45, usernameBytes.length);
        System.out.println("Put:" + put);

    }
}
