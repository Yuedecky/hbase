package com.broad.data.hbase.jmx;

public class Hello implements HelloMBean {

    private String hellName;

    @Override
    public String getHelloMBeanName() {
        return hellName;
    }

    @Override
    public void setHellMBeanName(String name) {

        this.hellName = name;
    }

    @Override
    public void printHelloMBeanInfo() {

        System.out.println(this.hellName);
    }
}
