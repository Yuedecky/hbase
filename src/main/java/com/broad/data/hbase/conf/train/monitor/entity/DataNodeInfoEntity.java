package com.broad.data.hbase.conf.train.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DataNodeInfoEntity implements Serializable {



    private String nodeName;

    private String nodeAddress;

    private double usedSpace;


    private String adminState;

    private int lastConnectCount;

    private double capacity;



    private int numBlock;


    private double nonHDFSSpace;


    private double remainingSpace;


    private double blockPoolUsed;

    private double blockPoolUsedPercent;


}
