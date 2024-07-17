package com.zzy.frontpluginmysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class FrontPluginMysqlApplication {

    private static final String SELECT_SQL = "SELECT * FROM student order by id";
    private static final String UPDATE_SQL = "UPDATE student SET name = '李四' WHERE id = 1";
    public static void main(String[] args) {
        SpringApplication.run(FrontPluginMysqlApplication.class, args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FrontPluginMysqlApplication.class, args);
        Mysql bean = applicationContext.getBean(Mysql.class);

        bean.initTable();
        log.info("=================== initTable end ====================");

        //插入数据
        bean.batchInsert();
        log.info("=================== insert data end ====================");

        //更新数据
        bean.execute(UPDATE_SQL);

        bean.executeQuery(SELECT_SQL);
    }

}
