package dev.fizlrock.todo.repository.relational;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import dev.fizlrock.todo.repository.relational.entity.ProjectAgregate;
import dev.fizlrock.todo.repository.relational.mapper.ProjectAgregateMapper;
import dev.fizlrock.todo.repository.relational.repository.ProjectAgregateRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** ProjectRepositoryRelationalImpl */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectRepositoryRelationalImpl implements IProjectRepository {

  @PostConstruct
  void init() {
    log.warn("Used Data-Jdbc(relational) repository implementation");
  }

  private final ProjectAgregateRepository repository;
  private final ProjectAgregateMapper mapper;

  @Override
  public void save(Project entity) {

    ProjectAgregate agregate = mapper.toAgregate(entity);
    log.info("agr: {}", agregate);
    repository.save(agregate);

    // throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void delete(Project entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public Optional<Project> findByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByName'");
  }

  @Override
  public Optional<Project> findById(UUID fromString) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public List<Project> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public List<Project> findAll(ProjectFilterRq rq) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }
}
