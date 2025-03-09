package dev.fizlrock.todo.domain.service;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.entity.Task;
import dev.fizlrock.todo.domain.exception.ProjectNameDublicateException;
import dev.fizlrock.todo.domain.exception.ProjectNotFoundException;
import dev.fizlrock.todo.domain.mapper.IMapProject;
import dev.fizlrock.todo.domain.mapper.IMapTask;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.ports.ITodoService;
import dev.fizlrock.todo.domain.service.dto.*;
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

  private Project getProjectById(String id) {

    UUID projectId = UUID.fromString(id);

    Project project =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    return project;
  }

  @Override
  public ProjectMsg createProject(ProjectCreateRq rq) {

    Optional<Project> nameConflict = projectRepository.findByName(rq.name());
    if (nameConflict.isPresent())
      throw new ProjectNameDublicateException(nameConflict.get().getName());

    var project =
        Project.createNewProject(rq.name(), rq.description(), rq.startTime(), rq.endTime());

    projectRepository.save(project);

    return projectMapper.toDto(project);
  }

  @Override
  public ProjectMsg updateProject(ProjectUpdateRq rq) {

    Project p = getProjectById(rq.id());
    p.setName(rq.project().name());
    p.setDescription(rq.project().description());
    p.setDates(rq.project().startTime(), rq.project().endTime());

    projectRepository.save(p);

    return projectMapper.toDto(p);
  }

  @Override
  public ProjectMsg getProject(ProjectGetRq rq) {
    Project p = getProjectById(rq.projectId());
    return projectMapper.toDto(p);
  }

  @Override
  public ProjectMsg deleteProject(ProjectDeleteRq rq) {
    Project p = getProjectById(rq.projectId());

    projectRepository.delete(p);

    return projectMapper.toDto(p);
  }

  @Override
  public ProjectListResp getAllProjects(ProjectFilterRq rq) {
    List<ProjectMsg> projects =
        projectRepository.findAll(rq).stream().map(projectMapper::toDto).toList();

    return new ProjectListResp(projects);
  }

  @Override
  public TaskListResp getTasksForProject(TasksInProjectRq rq) {
    Project p = getProjectById(rq.projectId());
    var tasks = p.getTasks().stream().map(taskMapper::toDto).toList();

    return new TaskListResp(tasks);
  }

  @Override
  public TaskMsg getTask(TaskRq rq) {

    Project project = getProjectById(rq.projectId());

    var task_id = UUID.fromString(rq.taskId());

    var task = project.getTaskById(task_id);

    return taskMapper.toDto(task);
  }

  @Override
  public TaskMsg createTask(TaskCreateRq rq) {
    Project project = getProjectById(rq.projectId());

    var task = project.addTask(rq.task().name(), rq.task().date());

    projectRepository.save(project);
    return taskMapper.toDto(task);
  }

  @Override
  public TaskMsg updateTask(TaskUpdateRq rq) {

    Project project = getProjectById(rq.projectId());

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
    var task_id = UUID.fromString(rq.taskId());

    Project project = getProjectById(rq.projectId());

    project.removeTaskById(task_id);

    projectRepository.save(project);
  }

  @Override
  public void deleteCompletedTasks(DeleteCompletedTasksRq rq) {
    Project project = getProjectById(rq.projectId());

    // check n + 1
    project.getTasks().stream()
        .filter(t -> t.isCompleted() == true)
        .map(Task::getId)
        .forEach(id -> project.removeTaskById(id));
  }
}
