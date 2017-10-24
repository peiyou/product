package com.btcc.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.btcc.datasource.ProgDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * Created by peiyou on 10/19/16.
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig{

    @Autowired
    private Environment env;

    @Bean(name = "btcchinaMkDataSource")
    public DataSource btcchinaMkDataSource() throws Exception{
        Properties props = new Properties();
        Map<String,String> map = DatasourceProperties.btcchinaMk;
        return getDataSource(props, map);
    }

    @Bean(name = "btcchinaPhpbbDataSource")
    public DataSource btcchinaPhpbbDataSource() throws Exception{
        Properties props = new Properties();
        Map<String,String> map = DatasourceProperties.btcchinaPhpbb;
        return getDataSource(props, map);
    }


    @Bean(name = "progDataSource")
    public DataSource progDataSource() throws Exception{
        Properties props = new Properties();
        Map<String,String> map = DatasourceProperties.prog;
        return getDataSource(props, map);
    }

    @Bean(name = "spotusdDataSource")
    public DataSource spotusdDataSource() throws Exception{
        Properties props = new Properties();
        Map<String,String> map = DatasourceProperties.spotusd;
        return getDataSource(props, map);
    }

    @Bean(name = "bttxDataSource")
    public DataSource bttxDataSource() throws Exception {
        Properties props = new Properties();
        Map<String,String> map = DatasourceProperties.bttx;
        return getDataSource(props, map);
    }

    /**
     * 注意@Primary注解， 表示默认的数据源，不加时，会报异常，只能有一个
     * @return
     * @throws Exception
     */
    @Bean(name = "reportDataSource")
    @Primary
    public DataSource reportDataSource() throws Exception{
        Properties props = new Properties();
        Map<String,String> map = DatasourceProperties.report;
        return getDataSource(props, map);
    }


    private DataSource getDataSource(Properties props, Map<String, String> map) throws Exception {
        props.put("driverClassName", map.get("driver-class-name"));
        props.put("url", map.get("url"));
        props.put("username", map.get("username"));
        props.put("password", map.get("password"));
        props.put("validationQuery", map.get("validation-query"));
        props.put("testWhileIdle", map.get("test-while-idle"));
        props.put("timeBetweenEvictionRunsMillis", map.get("time-between-eviction-runs-millis"));
        props.put("minEvictableIdleTimeMillis", map.get("min-evictable-idle-time-millis"));
        props.put("initialSize", map.get("initialSize"));
        props.put("minIdle", map.get("minIdle"));
        props.put("maxActive", map.get("maxActive"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * 配置SqlSessionFactory：
     * - 创建SqlSessionFactoryBean，并指定一个dataSource；
     * -
     * - 指定mapper文件的路径；
     */
    @Bean(name="btcchinaMkSqlSessionFactory")
    public SqlSessionFactory btcchinaMkSqlSessionFactory() throws Exception{

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(btcchinaMkDataSource());
        return getSqlSessionFactory(bean);

    }

    @Bean(name="btcchinaPhpbbSqlSessionFactory")
    public SqlSessionFactory btcchinaPhpbbSqlSessionFactory() throws Exception{

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(btcchinaPhpbbDataSource());
        //分页插件
        return getSqlSessionFactory(bean);
    }

    @Bean(name="reportSqlSessionFactory")
    public SqlSessionFactory reportSqlSessionFactory() throws Exception{

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(reportDataSource());
        //分页插件
        return getSqlSessionFactory(bean);
    }

    @Bean(name="progSqlSessionFactory")
    public SqlSessionFactory progSqlSessionFactory() throws Exception{

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(progDataSource());
        //分页插件
        return getSqlSessionFactory(bean);
    }

    @Bean(name="spotusdSqlSessionFactory")
    public SqlSessionFactory spotusdSqlSessionFactory() throws Exception{

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(spotusdDataSource());
        //分页插件
        return getSqlSessionFactory(bean);
    }

    @Bean(name="bttxSqlSessionFactory")
    public SqlSessionFactory bttxSqlSessionFactory() throws Exception{

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(bttxDataSource());
        //分页插件
        return getSqlSessionFactory(bean);
    }

    private SqlSessionFactory getSqlSessionFactory(SqlSessionFactoryBean bean) {
        //分页插件
        Interceptor[] interceptors = new Interceptor[]{pageHelper()};
        bean.setPlugins(interceptors);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置事务管理器
     */
      @Bean
      public DataSourceTransactionManager transactionManager() throws Exception {
          return new DataSourceTransactionManager(reportDataSource());
      }


    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();

        properties.setProperty("dialect", "mysql");
        /**
         * 该参数默认为false
         * 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用 -->
         * 和startPage中的pageNum效果一样
         */
        properties.setProperty("offsetAsPageNum", "true");

        /**
         * 该参数默认为false
         * 设置为true时，使用RowBounds分页会进行count查询
         */
        properties.setProperty("rowBoundsWithCount", "true");
        /**
         * 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
         *（相当于没有执行分页查询，但是返回结果仍然是Page类型)
         */
        properties.setProperty("pageSizeZero", "true");

        /**
         * 3.3.0版本可用 - 分页参数合理化，默认false禁用
         * 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
         * 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
         */
        properties.setProperty("reasonable", "true");
        /**
         * 3.5.0版本可用 - 为了支持startPage(Object params)方法
         * 增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值
         * 可以配置pageNum,pageSize,count,pageSizeZero,reasonable,不配置映射的用默认值
         * 不理解该含义的前提下，不要随便复制该配置
         */

        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
