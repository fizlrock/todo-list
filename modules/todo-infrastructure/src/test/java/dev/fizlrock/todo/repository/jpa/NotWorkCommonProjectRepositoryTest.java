package dev.fizlrock.todo.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.repository.relational.JdbcConfiguration;
import java.time.LocalDate;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.TestPropertySource;

// @Configuration
// @Import({DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
// @EnableJdbcRepositories
// @ComponentScan(basePackages = "dev.fizlrock.todo.repository.relational")
// class SomeConfig extends AbstractJdbcConfiguration {

// }
@Configuration
@Import({JdbcConfiguration.class})
@ComponentScan(basePackages = "dev.fizlrock.todo.repository.relational")
class JdbcTestConfiguration {

  @Bean(name = "transactionManager")
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}

// @Disabled
@SpringBootTest(classes = {JdbcTestConfiguration.class})
@RequiredArgsConstructor
@TestPropertySource(properties = "todo.repository.impl=relational")
public class NotWorkCommonProjectRepositoryTest {

  @Autowired ApplicationContext applicationContext; // Внедряем контекст

  @Autowired IProjectRepository repository;

  @Test
  void trueTest() {
    assertTrue(true); // Базовый тест
  }

  // @Test
  // void printAvailableBeans() {
  //   String[] beanNames = applicationContext.getBeanDefinitionNames();
  //   System.out.println("Доступные бины в подпакете 'dev.fizlrock.todo.repository.jpa':");
  //   for (String beanName : beanNames) {
  //     System.out.println(beanName);
  //   }
  //   assertTrue(beanNames.length > 0); // Проверяем, что бины есть
  // }

  @Test
  void saveNewProjectTest() {
    Project p =
        Project.createNewProject(
            "name", "description", LocalDate.now(), LocalDate.now().plusDays(10));

    repository.save(p);

    var loaded = repository.findById(p.getId());
    assertTrue(loaded.isPresent());
    var actual = loaded.get();

    System.out.println("Expected: " + p);
    System.out.println("Actual: " + actual);

    assertTrue(p.equals(actual));
  }

  @Test
  void findByNotExistingID() {
    var result = repository.findById(UUID.randomUUID());

    assertTrue(result.isEmpty());
  }

  @Test
  void saveProjectWithTasks() {
    Project project =
        Project.createNewProject(
            "New project", "description", LocalDate.now(), LocalDate.now().plusYears(1));

    project.addTask("first task", LocalDate.now().plusDays(10));
    project.addTask("second task", LocalDate.now().plusDays(10));

    repository.save(project);

    Project loadedProject = repository.findById(project.getId()).get();

    assertEquals(2, loadedProject.getTasks().size());
    assertEquals(project, loadedProject);
  }
}
