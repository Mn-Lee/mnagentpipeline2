package com.pedmn.mnagentpipeline2.common.dal.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * PostgreSQL/pgvector data source and MyBatis configuration.
 *
 * <p>This configuration is enabled only when the pgvector JDBC URL is
 * configured. pgvector DAO interfaces should be placed in
 * {@code common.dal.dao.pg} and their XML files in
 * {@code mapper/pg}.</p>
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(
        prefix = "mnagentpipeline.datasource.pgvector",
        name = "jdbc-url"
)
@MapperScan(
        basePackages = "com.pedmn.mnagentpipeline2.common.dal.dao.pg",
        sqlSessionFactoryRef = "pgVectorSqlSessionFactory"
)
public class MnAgentPgVectorDataSourceConfig {

    @Bean(name = "pgVectorDataSource")
    @ConfigurationProperties(prefix = "mnagentpipeline.datasource.pgvector")
    public HikariDataSource pgVectorDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "pgVectorSqlSessionFactory")
    public SqlSessionFactory pgVectorSqlSessionFactory(
            @Qualifier("pgVectorDataSource") DataSource pgVectorDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(pgVectorDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/pg/**/*.xml")
        );

        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }

    @Bean(name = "pgVectorTransactionManager")
    public PlatformTransactionManager pgVectorTransactionManager(
            @Qualifier("pgVectorDataSource") DataSource pgVectorDataSource) {
        return new DataSourceTransactionManager(pgVectorDataSource);
    }
}
