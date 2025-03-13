package dev.fizlrock.todo.restapi.controller;

import dev.fizlrock.todo.domain.ports.ITodoService;
import dev.fizlrock.todo.domain.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final ITodoService service;

  @GetMapping("/{taskId}")
  TaskMsg getTasks(
      @PathVariable("projectId") String projectId, @PathVariable("taskId") String taskId) {
    var rq = new TaskRq(projectId, taskId);
    return service.getTask(rq);
  }

  @GetMapping
  TaskListResp getTasks(@PathVariable("projectId") String projectId) {
    var rq = new TasksInProjectRq(projectId);
    return service.getTasksForProject(rq);
  }

  @PostMapping
  TaskMsg createTask(@PathVariable("projectId") String projectId, @RequestBody TaskNewMsg task) {
    var rq = new TaskCreateRq(projectId, task);
    return service.createTask(rq);
  }

  @PutMapping("/{taskId}")
  TaskMsg updateTask(
      @PathVariable("projectId") String projectId,
      @PathVariable("taskId") String taskId,
      @RequestBody TaskNewMsg task) {

    var rq = new TaskUpdateRq(projectId, taskId, task);
    return service.updateTask(rq);
  }

  @DeleteMapping("/{taskId}")
  void deleteTask(
      @PathVariable("projectId") String projectId, @PathVariable("taskId") String taskId) {
    service.deleteTask(new TaskRq(projectId, taskId));
  }

  @DeleteMapping
  void deleteCompletedTasks(@PathVariable("projectId") String projectId) {

    service.deleteCompletedTasks(new DeleteCompletedTasksRq(projectId));
  }
}
