package dev.fizlrock.todo.repository;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** ProjectRepository */
@Repository
public class ProjectRepository implements IProjectRepository {

  @Autowired ProjectJPARepository jpa;

  @Override
  public void save(Project entity) {
    jpa.save(entity);
  }

  @Override
  public void delete(Project entity) {
    jpa.delete(entity);
  }

  @Override
  public Optional<Project> findByName(String name) {
    return jpa.findByName(name);
  }

  @Override
  public Optional<Project> findById(UUID id) {
    return jpa.findById(id);
  }

  @Override
  public List<Project> findAll() {
    return jpa.findAll();
  }

  @Override
  public List<Project> findAll(ProjectFilterRq rq) {
    return this.findAll();
  }
}
