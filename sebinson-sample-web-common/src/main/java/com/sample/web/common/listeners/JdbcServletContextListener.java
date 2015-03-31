package com.sample.web.common.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JdbcServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("--------------------------------");
    }

    public void contextDestroyed(ServletContextEvent event) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gooagoo_sample?useUnicode=true&characterEncoding=UTF-8", "root", "sys@123");
            Statement stmt = conn.createStatement();
            stmt.execute("SHUTDOWN;");
            conn.close();
            System.out.println("jdbc connection closed！");
        } catch (Exception e) {
        }
    }
}