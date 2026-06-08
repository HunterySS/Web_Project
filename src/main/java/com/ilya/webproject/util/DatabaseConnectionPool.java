package com.ilya.webproject.util;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPool.class);
    private static final DataSource dataSource;

    static {
        PoolProperties properties = new PoolProperties();

        properties.setUrl("jdbc:postgresql://localhost:5432/web_project");
        properties.setUsername("postgres");
        properties.setPassword("root");
        properties.setDriverClassName("org.postgresql.Driver");

        properties.setInitialSize(5);
        properties.setMaxActive(10);
        properties.setMinIdle(5);
        properties.setMaxIdle(10);
        properties.setMaxWait(30000);

        properties.setValidationQuery("SELECT 1");
        properties.setTestOnBorrow(true);
        properties.setTestWhileIdle(true);

        properties.setTimeBetweenEvictionRunsMillis(30000);
        properties.setMinEvictableIdleTimeMillis(60000);

        dataSource = new DataSource();
        dataSource.setPoolProperties(properties);

        logger.info("Tomcat JDBC Pool initialized successfully");
    }

    private DatabaseConnectionPool() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("Tomcat JDBC Pool closed");
        }
    }
}