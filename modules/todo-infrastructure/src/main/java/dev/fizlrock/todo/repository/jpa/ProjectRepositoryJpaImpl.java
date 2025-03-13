package dev.fizlrock.todo.repository.jpa;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/** ProjectRepository */
@Repository
@RequiredArgsConstructor
@Slf4j
// @ConditionalOnProperty(name = "todo.repository.impl", havingValue = "jpa", matchIfMissing = true)
public class ProjectRepositoryJpaImpl implements IProjectRepository {

  @PostConstruct
  void init() {
    log.warn("Used JPA repository implementation");
  }

  private final ProjectJPARepository jpa;

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
    // TODO тут N+1 получается

    int page = (int) (rq.skip() / rq.limit()); // Номер страницы
    var pageRq = (Pageable) PageRequest.of(page, rq.limit().intValue());

    Specification<Project> filter =
        Specification.where(ProjectSpecification.hasNameLike(rq.name()))
            .and(ProjectSpecification.startAfter(rq.start()))
            .and(ProjectSpecification.endBefore(rq.end()));
    return jpa.findAll(filter, pageRq).toList();
  }
}
