package dev.fizlrock.todo.repository.mongo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/** MongoConfiguration */
@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "mongo")
@Configuration
@Import({MongoAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "dev.fizlrock.todo.repository.mongo")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

  @Override
  protected String getDatabaseName() {
    return "todolist";
  }
}
