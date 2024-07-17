package com.zzy.frontpluginmysql;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * @author zzypersonally@gmail.com
 * @description
 * @since 2024/5/27 13:34
 */
@Service
@Slf4j
@AllArgsConstructor
public class Mysql {
    private final DataSource dataSource;


    public void initTable() {
        //不存在则新建表
        String sql = "CREATE TABLE IF NOT EXISTS student (id INT PRIMARY KEY, name VARCHAR(50), age INT, phone VARCHAR(20), address VARCHAR(100))";
        execute(sql);
        //清空表
        execute("TRUNCATE TABLE student");
    }


    public void execute(String sql) {
        try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (Exception ignore) {
        }
    }

    //batchInsert
    public void batchInsert() {
        try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            for (int i = 0; i < 10; i++) {
                String insertSql = "INSERT INTO student (id, name, age, phone, address) VALUES (" + i + ", '张三', 20, '12345678901', '北京市朝阳区')";
                statement.addBatch(insertSql);
            }
            statement.executeBatch();
            conn.setAutoCommit(true);
        } catch (Exception ignore) {
        }
    }

    //executeQuery
    public void executeQuery(String sql) {
        try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            //遍历result ，此处字段名字和数量未知，需要从metadata中获取
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    log.info(metaData.getColumnName(i) + " : " + resultSet.getString(i));
                }
            }
        } catch (Exception ignore) {
        }
    }
}