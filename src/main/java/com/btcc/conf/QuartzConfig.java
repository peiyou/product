package com.btcc.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by peiyou on 2016/11/3.
 */
@Configuration
public class QuartzConfig {

    @Bean(name = "schedulerFactory")
    public SchedulerFactoryBean schedulerFactory(){
        SchedulerFactoryBean bean = new SchedulerFactoryBean ();
        return bean;
    }

}
