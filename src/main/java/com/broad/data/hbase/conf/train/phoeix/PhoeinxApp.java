package com.broad.data.hbase.conf.train.phoeix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhoeinxApp {


    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        Connection connection = DriverManager.getConnection("jdbc:phoenix:localhost:2181");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PERSON");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }
        statement.close();
        connection.close();
    }
}
