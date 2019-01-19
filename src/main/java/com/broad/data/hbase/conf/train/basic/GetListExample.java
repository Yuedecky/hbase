package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetListExample {

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();

        HBaseHelper helper = HBaseHelper.getHelper(configuration);

        if (!helper.existsTable(TableName.valueOf("fileInfo"))) {
            helper.createTable("fileInfo", "pathInfo", "saveInfo");
        }

        List<Get> gets = new ArrayList<>();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("fileInfo"));
        byte[] pathUrl = Bytes.toBytes("/usr/local/bin");

        byte[] pathQual = Bytes.toBytes("admin");

        byte[] pathRow = Bytes.toBytes("pathInfo");
        Get get1 = new Get(pathRow);
        get1.addColumn(pathRow, pathQual);

        byte[] pathUrl2 = Bytes.toBytes("/opt/soft/data");

        byte[] pathQual2 = Bytes.toBytes("root");
        byte[] pathRow2 = Bytes.toBytes("pathInfo");

        Get get2 = new Get(pathRow2);

        get2.addColumn(pathUrl2, pathQual2);

        gets.add(get1);
        gets.add(get2);

        Result[] result = table.get(gets);


        for (Result result1 : result) {
            String row = Bytes.toString(result1.getRow());
            System.out.println("Row:" + row);

            byte[] val = null;
            if(result1.containsColumn(pathUrl,pathQual)){
                val = result1.getValue(pathUrl,pathQual);
                System.out.println(Bytes.toString(val));
            }
            if(result1.containsColumn(pathUrl,pathQual2)){
                val = result1.getValue(pathUrl,pathQual2);
                System.out.println(Bytes.toString(val));
            }
        }

        System.out.println("second iteration...");

        for(Result result1:result){
            for(Cell cell:result1.listCells()){
                System.out.println("Row:"+Bytes.toString(cell.getRowArray(),cell.getRowLength())+",value:"+Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
        table.close();
        connection.close();
        helper.close();


    }
}
