package com.broad.data.hbase.jmx;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class ServerConfigureAgent {

    private MBeanServer mbs;

    private ObjectName name;

    public ServerConfigureAgent() {
        this.mbs = MBeanServerFactory.createMBeanServer("ServerConfigureAgent");
        ServerConfigure serverConfigure = new ServerConfigure(9099, "localhost");

        try {
            this.name = new ObjectName("ServerAgent:port=9099,host=localhost");
            mbs.registerMBean(serverConfigure, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObjectName getName() {
        return name;
    }

    private void startAdaptor() {
        HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();

        MBeanInfo mbeanInfo = htmlAdaptorServer.getMBeanInfo();
        ObjectName adaptorName;
        try {

            System.out.println(mbeanInfo.getDescriptor());
            adaptorName = new ObjectName("ServerConfigureAdaptor:name=" + mbeanInfo.getDescriptor());
            htmlAdaptorServer.setPort(9099);
            htmlAdaptorServer.addNotificationListener(new ServerConfigureChangedListener(),
                    null,null);
            mbs.registerMBean(htmlAdaptorServer, adaptorName);
            htmlAdaptorServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        System.out.println("serverConfigure agent is running");
        ServerConfigureAgent agent = new ServerConfigureAgent();
        agent.startAdaptor();
    }
}
