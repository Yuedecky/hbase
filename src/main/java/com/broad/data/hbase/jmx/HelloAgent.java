package com.broad.data.hbase.jmx;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.*;

//
public class HelloAgent extends NotificationBroadcasterSupport  {

    private MBeanServer mbs;

    public HelloAgent() {
        this.mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        Hello hello = new Hello();
        ObjectName helloName;
        try {
            helloName = new ObjectName("HelloAgent:name=hello");
            mbs.registerMBean(hello, helloName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void startHtmlAdapterServer() {
        HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();

        ObjectName adaptorName;
        try {
            adaptorName = new ObjectName("HelloAgent:name=htmladapter,port=9092");
            htmlAdaptorServer.setPort(9092);
            mbs.registerMBean(htmlAdaptorServer,adaptorName);
            htmlAdaptorServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        System.out.println("hello agent is running");
        HelloAgent agent = new HelloAgent();
        agent.startHtmlAdapterServer();
    }


}
