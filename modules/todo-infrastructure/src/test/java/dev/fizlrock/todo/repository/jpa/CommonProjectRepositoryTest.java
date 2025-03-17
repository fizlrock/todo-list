package dev.fizlrock.todo.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(basePackages = "dev.fizlrock.todo.repository")
class ProjectRepositoryTestConfiguration {}

@SpringBootTest(classes = {ProjectRepositoryTestConfiguration.class})
@RequiredArgsConstructor
// @TestPropertySource(properties = "todo.repository.impl=relational")
@TestPropertySource(properties = "todo.repository.impl=mongo")
@Slf4j
public class CommonProjectRepositoryTest {

  @Autowired ApplicationContext applicationContext; // Внедряем контекст

  @Autowired IProjectRepository repository;

  @Test
  void trueTest() {
    assertTrue(true); // Базовый тест
  }

  @Test
  void saveNewProjectTest() {
    log.info("creating and saving new project");
    Project p =
        Project.createNewProject(
            "name", "description", LocalDate.now(), LocalDate.now().plusDays(10));
    repository.save(p);

    log.info("loading and checking saved project");

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

    log.info("creating and saving new project");
    Project project =
        Project.createNewProject(
            "New project", "description", LocalDate.now(), LocalDate.now().plusYears(1));

    project.addTask("first task", LocalDate.now().plusDays(10));
    project.addTask("second task", LocalDate.now().plusDays(10));

    repository.save(project);
    log.info("loading and checking saved project");

    Project loadedProject = repository.findById(project.getId()).get();
    assertEquals(2, loadedProject.getTasks().size());
    assertEquals(project, loadedProject);
  }

  @Test
  void editProject() {
    log.info("creating and saving new project");
    Project project =
        Project.createNewProject(
            "Some other project", "description", LocalDate.now(), LocalDate.now().plusYears(1));

    project.addTask("first task", LocalDate.now().plusDays(10));
    project.addTask("second task", LocalDate.now().plusDays(10));
    repository.save(project);

    log.info("loading saved project and changing");

    var optional_loaded_project = repository.findById(project.getId());
    assertTrue(optional_loaded_project.isPresent());
    log.info("project loaded");

    var loaded = optional_loaded_project.get();

    loaded
        .getTasks()
        .forEach(
            t -> {
              loaded.updateTask(t.getId(), t.getName() + "changed", t.getFinishDate(), true);
            });
    loaded.addTask("Third task", loaded.getStartDate().plusDays(1));
    System.out.println(loaded);

    log.info("saving changed project");
    repository.save(loaded);

    log.info("checking saved project");

    var changed_loaded = repository.findById(project.getId()).get();
    assertEquals(changed_loaded, loaded);
  }
}
