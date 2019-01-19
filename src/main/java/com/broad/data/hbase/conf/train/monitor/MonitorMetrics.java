package com.broad.data.hbase.conf.train.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonitorMetrics {

    List<Map<String,Object>> beans = new ArrayList<>();

    public List<Map<String, Object>> getBeans() {
        return beans;
    }

    public void setBeans(List<Map<String, Object>> beans) {
        this.beans = beans;
    }

    public Object getMetricsValue(String name){
        for(Map<String,Object> bean:beans){
            if(bean.containsKey(name)){
                return bean.get(name);
            }
        }
        return null;
    }
}
