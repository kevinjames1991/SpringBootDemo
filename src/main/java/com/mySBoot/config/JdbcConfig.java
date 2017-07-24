package com.mySBoot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class JdbcConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConfig.class);

    @Bean(initMethod = "init", destroyMethod = "close", name = "dataSource")
    DataSource dataSource(DataSourceProperties config) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setUrl(config.getUrl());
        dataSource.setDriverClassName(config.getDriverClassName());

        //configuration
        dataSource.setInitialSize(config.getInitialSize());
        dataSource.setMinIdle(config.getMinIdle());
        dataSource.setMaxActive(config.getMaxActive());
        dataSource.setMaxWait(config.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(config.getValidationQuery());
        dataSource.setTestWhileIdle(config.isTestWhileIdle());
        dataSource.setTestOnBorrow(config.isTestOnBorrow());
        dataSource.setTestOnReturn(config.isTestOnReturn());
        dataSource.setPoolPreparedStatements(config.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(config.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            dataSource.setFilters(config.getFilters());
        } catch (SQLException e) {
            LOGGER.error("druid configuration initialization filter", e);
        }
        dataSource.setConnectionProperties(config.getConnectionProperties());
        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
