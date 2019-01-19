package com.broad.data.hbase.conf.train.basic;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellComparator;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CellComparatorExample {

    public static void main(String[] args) throws IOException {


        Put put1 = new Put(Bytes.toBytes("row-1"));

        put1.addColumn(Bytes.toBytes("fam-1"), Bytes.toBytes("qual-1"), Bytes.toBytes("val-1"));

        Put put2 = new Put(Bytes.toBytes("row-2"));

        put2.addColumn(Bytes.toBytes("fam-1"), Bytes.toBytes("qual-2"), Bytes.toBytes("val-2"));


        Put put3 = new Put(Bytes.toBytes("row-3"));

        put3.addColumn(Bytes.toBytes("fam-2"), Bytes.toBytes("qual-3"),
                Bytes.toBytes("val-3"));

        Put put4 = new Put(Bytes.toBytes("row-1"));
        put4.addColumn(Bytes.toBytes("fam-2"), Bytes.toBytes("qual-2"), Bytes.toBytes("val-2"));

        CellComparator cellComparator = CellComparator.getInstance();


        ArrayList<Cell> cells = new ArrayList<>();
        Put[] puts = new Put[]{put1, put2, put3, put4};


        for (Put put : puts) {
            CellScanner scanner = put.cellScanner();
            while (scanner.advance()) {
                Cell cell = scanner.current();
                cells.add(cell);
            }
        }

        System.out.println("Shuffling...");



        Collections.shuffle(cells);

        for (Cell cell : cells) {
            System.out.println("Cell: " + cell);
        }

        System.out.println("Sorting...");
        Collections.sort(cells, cellComparator);

        for (Cell cell : cells) {
            System.out.println("Cell: " + cell);
        }

    }
}
