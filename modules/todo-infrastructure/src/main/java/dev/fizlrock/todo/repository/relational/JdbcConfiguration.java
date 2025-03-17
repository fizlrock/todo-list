package dev.fizlrock.todo.repository.relational;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/** JdbcConfiguration */
@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "relational")
@Configuration
@Import({
  DataSourceAutoConfiguration.class,
  DataSourceTransactionManagerAutoConfiguration.class,
  JdbcTemplateAutoConfiguration.class,
  LiquibaseAutoConfiguration.class
})
@EnableJdbcRepositories(basePackages = "dev.fizlrock.todo.repository.relational")
public class JdbcConfiguration extends AbstractJdbcConfiguration {}
