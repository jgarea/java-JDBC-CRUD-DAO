/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.mariadb.javacrud.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Juan
 */
public class MyDataSource {
    private static HikariConfig config=new HikariConfig();
    private static HikariDataSource dataSource;
    
    static{
        config.setJdbcUrl("jdbc:mariadb://localhost/empresa?allowPublicKeyRetrieval=true" +
                "&useSSL=false&useUnicode=true&serverTimezone=Europe/Madrid");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("maximumPoolSize", 1);
        // propiedades propuestas en la web de HikariCP para MySQL
        // se puede consultar más en https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        dataSource = new HikariDataSource(config);
        
    }
    private MyDataSource(){}
    
    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}
