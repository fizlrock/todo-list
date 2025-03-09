package dev.fizlrock.todo.domain.ports;

import dev.fizlrock.todo.domain.service.dto.*;

public interface ITodoService {
  public ProjectMsg createProject(ProjectCreateRq rq);

  public ProjectMsg updateProject(ProjectUpdateRq rq);

  public ProjectMsg getProject(ProjectGetRq rq);

  public ProjectMsg deleteProject(ProjectDeleteRq rq);

  public ProjectListResp getAllProjects(ProjectFilterRq rq);

  public TaskListResp getTasksForProject(TasksInProjectRq rq);

  public TaskMsg getTask(TaskRq rq);

  public TaskMsg createTask(TaskCreateRq rq);

  public TaskMsg updateTask(TaskUpdateRq rq);

  public void deleteTask(TaskRq rq);

  public void deleteCompletedTasks(DeleteCompletedTasksRq rq);
}
