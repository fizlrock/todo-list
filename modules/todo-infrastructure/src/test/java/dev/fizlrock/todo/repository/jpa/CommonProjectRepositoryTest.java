package dev.fizlrock.todo.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = DataJdbcTestConfig.class)
@RequiredArgsConstructor
@TestPropertySource(properties = "todo.repository.impl=relational")
public class CommonProjectRepositoryTest {

  @Autowired private ApplicationContext applicationContext; // Внедряем контекст

  @Autowired private IProjectRepository repository;

  @Test
  void trueTest() {
    assertTrue(true); // Базовый тест
  }

  @Test
  void printAvailableBeans() {
    String[] beanNames = applicationContext.getBeanDefinitionNames();
    System.out.println("Доступные бины в подпакете 'dev.fizlrock.todo.repository.jpa':");
    for (String beanName : beanNames) {
      System.out.println(beanName);
    }
    assertTrue(beanNames.length > 0); // Проверяем, что бины есть
  }

  @Test
  void saveProjectTest() {
    Project p =
        Project.createNewProject(
            "name", "description", LocalDate.now(), LocalDate.now().plusDays(10));

    repository.save(p);

    var loaded = repository.findById(p.getId());
    assertTrue(loaded.isPresent());

    assertEquals(p, loaded.get());
  }
}
