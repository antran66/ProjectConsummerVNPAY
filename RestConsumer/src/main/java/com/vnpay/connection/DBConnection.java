package com.vnpay.connection;

import com.vnpay.common.LoadConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private static HikariDataSource ds;

    static {
        try {
            PropertiesConfiguration properties = LoadConfiguration.getInstance();

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getString("DATABASE_URL"));
            config.setUsername(properties.getString("DATABASE_USER"));
            config.setPassword(properties.getString("DATABASE_PASS"));
            config.setMaximumPoolSize(properties.getInt("MAX_POOL_SIZE"));
            config.setMinimumIdle(properties.getInt("MIN_IDLE"));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            ds = new HikariDataSource(config);
        }catch(Exception e) {
            logger.error("Fail to create hikari connection pool: ", e);
        }
    }
    private DBConnection() {}

    /**
     * create database connection pool
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closeConnection(){
        if(ds != null){
            ds.close();
        }
    }
}
