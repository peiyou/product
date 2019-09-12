package com.btcc.conf;

import com.btcc.datasource.*;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

/**
 * Created by peiyou on 10/19/16.
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MybatisMapperScannerConfigurer {



    @Bean
    public MapperScannerConfigurer reportMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("reportSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(ReportDataSource.class);
        return mapperScannerConfigurer;
    }


}
