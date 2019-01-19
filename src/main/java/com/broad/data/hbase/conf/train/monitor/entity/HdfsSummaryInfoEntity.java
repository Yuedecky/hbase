package com.broad.data.hbase.conf.train.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class HdfsSummaryInfoEntity implements Serializable {


    private double total;

    private double dfsUsed;

    private double percentUsed;

    private double dfsFree;

    private String safeMode;

    private int totalBlocks;

    private int totalFiles;

    private List<DataNodeInfoEntity> deadDataNodes;


    private int numLiveDataNodes;

    //
    private int volumeFailuresTotal;



}
