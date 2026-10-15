package com.pedmn.mnagentpipeline2.common.dal.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * MySQL business data source and MyBatis configuration.
 *
 * <p>MySQL is the primary data source. Existing DAO and transaction code can
 * continue to use the default SqlSessionFactory and transaction manager.</p>
 */
@Configuration(proxyBeanMethods = false)
@MapperScan(
        basePackages = "com.pedmn.mnagentpipeline2.common.dal.dao.mysql",
        sqlSessionFactoryRef = "sqlSessionFactory"
)
public class MnAgentMysqlDataSourceConfig {

    @Bean(name = "mysqlDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties mysqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource mysqlDataSource(
            @Qualifier("mysqlDataSourceProperties") DataSourceProperties mysqlDataSourceProperties) {
        return mysqlDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactory(
            @Qualifier("dataSource") DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mysqlDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/mysql/**/*.xml")
        );

        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("dataSource") DataSource mysqlDataSource) {
        return new DataSourceTransactionManager(mysqlDataSource);
    }
}
