package com.zzy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class FrontPluginOracleApplication  {

    public static void main(String[] args) {
        SpringApplication.run(FrontPluginOracleApplication.class, args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FrontPluginOracleApplication.class, args);
        Oracle bean = applicationContext.getBean(Oracle.class);



//        //模糊查询
        bean.executeQuery("select * from STUDENT where id like '%9' order by id");

        //查询数据
        bean.executeQuery(Oracle.SELECT_SQL);

        //插入数据
        bean.batchInsert();
        log.info("=================== insert data end ====================");

        //更新数据
        bean.execute(Oracle.UPDATE_SQL);

        bean.executeQuery(Oracle.SELECT_SQL);

        //如果报错  Caused by: javax.management.InstanceAlreadyExistsException: org.springframework.boot:type=Admin,name=SpringApplication
        //请参考：https://blog.csdn.net/bin_zi_123/article/details/103135584
    }

}
