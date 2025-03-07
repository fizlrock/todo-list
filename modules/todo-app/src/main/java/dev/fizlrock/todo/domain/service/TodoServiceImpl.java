package dev.fizlrock.todo.domain.service;

import dev.fizlrock.todo.domain.dto.*;
import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.entity.Task;
import dev.fizlrock.todo.domain.mapper.IMapProject;
import dev.fizlrock.todo.domain.mapper.IMapTask;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.ports.ITodoService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TodoServiceImpl implements ITodoService {

  @Autowired IProjectRepository projectRepository;
  @Autowired IMapProject projectMapper;
  @Autowired IMapTask taskMapper;

  @Override
  public ProjectMsg createProject(ProjectCreateRq rq) {

    // TODO наверное стоит вынести эту логику в валидаторы
    Optional<Project> nameConflict = projectRepository.findByName(rq.name());
    if (nameConflict.isPresent()) throw new IllegalStateException("project name dublicate");

    var project =
        Project.createNewProject(rq.name(), rq.description(), rq.startTime(), rq.endTime());

    projectRepository.save(project);

    return projectMapper.toDto(project);
  }

  @Override
  public ProjectMsg updateProject(ProjectUpdateRq rq) {

    Project p =
        projectRepository
            .findById(UUID.fromString(rq.id()))
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));

    p.setName(rq.project().name());
    p.setDescription(rq.project().description());
    p.setDates(rq.project().startTime(), rq.project().endTime());

    projectRepository.save(p);

    return projectMapper.toDto(p);
  }

  @Override
  public ProjectMsg getProject(ProjectGetRq rq) {
    Project p =
        projectRepository
            .findById(UUID.fromString(rq.projectId()))
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));
    return projectMapper.toDto(p);
  }

  @Override
  public ProjectMsg deleteProject(ProjectDeleteRq rq) {
    Project p =
        projectRepository
            .findById(UUID.fromString(rq.projectId()))
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));

    projectRepository.delete(p);
    return projectMapper.toDto(p);
  }

  @Override
  public ProjectListResp getAllProjects(ProjectFilterRq rq) {
    // TODO ой как плохо
    // TODO ну хоть пагинацию сюда сделать
    // TODO да и вообще CQRS тут нужен

    List<Project> projects = projectRepository.findAll();

    var resp =
        projects.stream()
            .filter(p -> rq.name() == null || p.getName().contains(rq.name()))
            .filter(p -> rq.start() == null || p.getStartDate().isAfter(rq.start()))
            .filter(p -> rq.end() == null || p.getEndDate().isBefore(rq.end()))
            .skip(rq.skip())
            .limit(rq.limit())
            .map(projectMapper::toDto)
            .toList();

    return new ProjectListResp(resp);
  }

  @Override
  public TaskListResp getTasksForProject(TasksInProjectRq rq) {
    Project p =
        projectRepository
            .findById(UUID.fromString(rq.projectId()))
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));
    var tasks = p.getTasks().stream().map(taskMapper::toDto).toList();

    return new TaskListResp(tasks);
  }

  @Override
  public TaskMsg getTask(TaskRq rq) {

    var project_id = UUID.fromString(rq.projectId());
    var task_id = UUID.fromString(rq.taskId());

    Project project =
        projectRepository
            .findById(project_id)
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));

    Task task =
        project.getTasks().stream()
            .filter(t -> t.getId().equals(task_id))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("Task id not found"));

    return taskMapper.toDto(task);
  }

  @Override
  public TaskMsg createTask(TaskCreateRq rq) {
    var project_id = UUID.fromString(rq.projectId());

    Project project =
        projectRepository
            .findById(project_id)
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));

    var task = project.addTask(rq.task().name(), rq.task().date());

    projectRepository.save(project);
    return taskMapper.toDto(task);
  }

  @Override
  public TaskMsg updateTask(TaskUpdateRq rq) {
    var project_id = UUID.fromString(rq.projectId());

    Project project =
        projectRepository
            .findById(project_id)
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));
    var task =
        project.updateTask(
            UUID.fromString(rq.taskId()),
            rq.task().name(),
            rq.task().date(),
            rq.task().completed());

    projectRepository.save(project);

    return taskMapper.toDto(task);
  }

  @Override
  public void deleteTask(TaskRq rq) {
    var project_id = UUID.fromString(rq.projectId());
    var task_id = UUID.fromString(rq.taskId());

    Project project =
        projectRepository
            .findById(project_id)
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));
    project.removeTask(task_id);
    projectRepository.save(project);
  }

  @Override
  public void deleteCompletedTasks(DeleteCompletedTasksRq rq) {
    var project_id = UUID.fromString(rq.projectId());

    Project project =
        projectRepository
            .findById(project_id)
            .orElseThrow(() -> new IllegalArgumentException("Project id not found"));

    project.getTasks().stream()
        .filter(t -> t.isCompleted() == true)
        .map(Task::getId)
        .forEach(id -> project.removeTask(id));
  }
}
