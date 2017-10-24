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
    public MapperScannerConfigurer btcchinaMkMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("btcchinaMkSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(MkDataSource.class);

        return mapperScannerConfigurer;
    }

    @Bean
    public MapperScannerConfigurer btcchinaPhpbbMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("btcchinaPhpbbSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(PhpbbDatasource.class);
        return mapperScannerConfigurer;
    }

    @Bean
    public MapperScannerConfigurer reportMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("reportSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(ReportDataSource.class);
        return mapperScannerConfigurer;
    }


    @Bean
    public MapperScannerConfigurer progMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("progSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(ProgDataSource.class);
        return mapperScannerConfigurer;
    }

    @Bean
    public MapperScannerConfigurer prousdMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("spotusdSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(SpotusdDataSource.class);
        return mapperScannerConfigurer;
    }

    @Bean
    public MapperScannerConfigurer bttxMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("bttxSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.btcc.**.mapper");
        mapperScannerConfigurer.setAnnotationClass(BttxDataSource.class);
        return mapperScannerConfigurer;
    }
}
