package dev.fizlrock.todo.domain.ports;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** ProjectRepository */
public interface IProjectRepository extends CrudRepository<Project> {

  Optional<Project> findByName(String name);

  Optional<Project> findById(UUID fromString);

  List<Project> findAll();

  List<Project> findAll(ProjectFilterRq rq);
}
