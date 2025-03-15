package dev.fizlrock.todo.repository.jpa;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.*;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = "dev.fizlrock.todo.repository.relational")
@EnableJdbcRepositories(basePackages = "dev.fizlrock.todo.repository.relational")
public class DataJdbcTestConfig extends AbstractJdbcConfiguration {

  @Bean
  public DataSource dataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();

    dataSource.setURL(
        "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;IGNORECASE=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE");
    dataSource.setUser("sa");
    dataSource.setPassword("");
    return dataSource;
  }

  @Bean
  public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public JdbcMappingContext jdbcMappingContext() {
    return new JdbcMappingContext();
  }

  // Инициализация схемы
  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);

    // SQL скрипт для создания таблиц
    Resource databaseScript = new ClassPathResource("schema.sql");
    initializer.setDatabasePopulator(new ResourceDatabasePopulator(databaseScript));

    return initializer;
  }
}
