package com.zzy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class FrontPluginClickHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontPluginClickHouseApplication.class, args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FrontPluginClickHouseApplication.class, args);
        ClickHouse bean = applicationContext.getBean(ClickHouse.class);

        //初始化数据
        bean.initTable();

        //查询数据
        bean.executeQuery(ClickHouse.SELECT_SQL);

        //模糊查询
        bean.executeQuery("select * from student where name like '_四_'");

        //插入数据
        bean.batchInsert();
        log.info("=================== insert data end ====================");

        //更新数据
        bean.execute(ClickHouse.UPDATE_SQL);

        bean.executeQuery(ClickHouse.SELECT_SQL);

        //如果报错  Caused by: javax.management.InstanceAlreadyExistsException: org.springframework.boot:type=Admin,name=SpringApplication
        //请参考：https://blog.csdn.net/bin_zi_123/article/details/103135584
    }

}
