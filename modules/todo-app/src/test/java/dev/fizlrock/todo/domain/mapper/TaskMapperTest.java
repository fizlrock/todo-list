package dev.fizlrock.todo.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.fizlrock.todo.domain.entity.Project;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class TaskMapperTest {

  // TODO ужас
  IMapTask taskMapper = new IMapTaskImpl();
  IMapProject projectMapper = new IMapProjectImpl();

  @Test
  void mapTest() {

    var project =
        Project.createNewProject(
            "first project", "description", LocalDate.now(), LocalDate.now().plusDays(12));

    var projectDto = projectMapper.toDto(project);
    System.out.println(projectDto);

    assertEquals(projectDto.id(), project.getId().toString());
    assertEquals(projectDto.description(), project.getDescription());
    assertEquals(projectDto.endDate(), project.getEndDate());
    assertEquals(projectDto.startDate(), project.getStartDate());

    var task = project.addTask("Хей", LocalDate.now().plusDays(2));

    var taskDto = taskMapper.toDto(task);
    System.out.println(taskDto);

    assertEquals(taskDto.name(), task.getName());
    assertEquals(taskDto.id(), task.getId().toString());
    assertEquals(taskDto.completed(), task.isCompleted());
    assertEquals(taskDto.date(), task.getFinishDate());
  }
}
