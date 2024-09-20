package com.zzy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class ClickHouse {
    private final DataSource dataSource;

    static final String SELECT_SQL = "SELECT * FROM student order by id";
    static final String UPDATE_SQL = "UPDATE student SET name = '李四' WHERE id = 1";

    public void initTable() {
        //新建表
        try {
            execute("CREATE TABLE student (id UInt32, name String, age UInt8, address String, PRIMARY KEY (id)) ENGINE = MergeTree() ORDER BY id;");
        } catch (Exception ignore) {

        }
        //清空表
        execute("TRUNCATE TABLE student");
        batchInsert();
    }


    public void execute(String sql) {
        try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    //batchInsert
    public void batchInsert() {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO student (id, name, age, address) values (?,?,?,?)");
            for (int i = 0; i < 1000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "张三" + i);
                preparedStatement.setInt(3, i + 1);
                preparedStatement.setString(4, "地址" + i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}