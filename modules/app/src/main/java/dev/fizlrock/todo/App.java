package dev.fizlrock.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(
    exclude = {
      MongoAutoConfiguration.class,
      MongoRepositoriesAutoConfiguration.class,
      DataSourceAutoConfiguration.class,
      JdbcRepositoriesAutoConfiguration.class,
      LiquibaseAutoConfiguration.class
    })
public class App {
  public static void main(String[] args) {

    SpringApplication.run(App.class, args);
    System.out.println("HEY");
  }
}
