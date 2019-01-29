package com.broad.data.hbase.conf.train.coprocessor;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessor;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.coprocessor.RegionObserver;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.wal.WALEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class RegionObserverApp implements RegionObserver, RegionCoprocessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionObserverApp.class);
    static Connection connection = null;
    static Table table = null;
    static final String TABLE = "region-observer-table";


    static {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "localhost:2181");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            table = connection.getTable(TableName.valueOf(TABLE));
        } catch (IOException e) {
        }
    }

    @Override
    public void start(CoprocessorEnvironment env) {

    }

    @Override
    public void stop(CoprocessorEnvironment env) {

    }


    /**
     * 对col-fam1:col-qua1 进行累加操作
     *
     * @param c
     * @param put
     * @param edit
     * @param durability
     */
    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> c, Put put, WALEdit edit, Durability durability) throws IOException {

        byte[] columnFamily = Bytes.toBytes("col-fam1");
        byte[] columnQualifier = Bytes.toBytes("col-qua1");
        if (put.has(columnFamily, columnQualifier)) {

            //获取old col-fam1:col-qua1
            Result result = c.getEnvironment().getRegion().get(new Get(put.getRow()));
            int oldNum = 0;
            for (Cell cell : result.rawCells()) {
                if (CellUtil.matchingColumn(cell, columnFamily, columnQualifier)) {
                    oldNum = Integer.valueOf(Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
            //获取新的
            List<Cell> cellList = put.get(columnFamily, columnQualifier);
            int newCellNum = 0;

        }
    }


}
