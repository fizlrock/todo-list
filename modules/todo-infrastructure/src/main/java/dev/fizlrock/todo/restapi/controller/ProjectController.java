package dev.fizlrock.todo.restapi.controller;

import dev.fizlrock.todo.domain.dto.*;
import dev.fizlrock.todo.domain.ports.ITodoService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** ProjectController */
@RestController
@RequestMapping("/api/project")
@Slf4j
public class ProjectController {
  @Autowired ITodoService service;

  @PostMapping("/")
  ProjectMsg createNewProject(@RequestBody ProjectCreateRq rq) {
    return service.createProject(rq);
  }

  @PutMapping("/{projectId}")
  ProjectMsg updateProject(@PathVariable("projectId") String id, @RequestBody ProjectCreateRq rq) {

    return service.updateProject(new ProjectUpdateRq(id, rq));
  }

  @DeleteMapping("/{projectId}")
  void deleteProject(@PathVariable("projectId") String id) {

    service.deleteProject(new ProjectDeleteRq(id));
  }

  @GetMapping("/{projectId}")
  ProjectMsg getProject(@PathVariable("projectId") String id) {
    return service.getProject(new ProjectGetRq(id));
  }

  @GetMapping("/all")
  ProjectListResp getAllProjects(
      @RequestParam(name = "skip", required = true) Long skip,
      @RequestParam(name = "limit", required = true) Long limit,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "start", required = false) LocalDate start,
      @RequestParam(name = "end", required = false) LocalDate end) {

    var rq = new ProjectFilterRq(skip, limit, name, start, end);
    var resp = service.getAllProjects(rq);
    log.info("resp: {}", resp);

    return resp;
  }
}
