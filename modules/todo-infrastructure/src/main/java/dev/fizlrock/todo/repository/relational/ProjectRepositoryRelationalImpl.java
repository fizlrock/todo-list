package dev.fizlrock.todo.repository.relational;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import dev.fizlrock.todo.repository.relational.mapper.ProjectAgregateMapper;
import dev.fizlrock.todo.repository.relational.repository.ProjectAgregateRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

// тема на подумать
// 1 путь
// создаем ProjectAgregate и TaskEntity
// выгружаем всё это из БД
// мапим на Project
// 2 путь
// как-то конфигурируем Spring Data JDBC...

@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "relational")
@Component
public class ProjectRepositoryRelationalImpl implements IProjectRepository {

  @Autowired ProjectAgregateRepository repository;
  @Autowired JdbcAggregateTemplate jdbcTemplate;
  @Autowired ProjectAgregateMapper mapper;

  @Override
  public Optional<Project> findByName(String name) {
    return repository.findByName(name).map(mapper::toDomain);
  }

  @Override
  public Optional<Project> findById(UUID id) {
    return repository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Project save(Project p) {

    var agregate = mapper.toAgregate(p);
    if (this.findById(p.getId()).isEmpty()) jdbcTemplate.insert(agregate);
    else jdbcTemplate.update(agregate);
    return p;
  }

  @Override
  public void delete(Project p) {
    repository.delete(mapper.toAgregate(p));
  }

  @Override
  public List<Project> findAll() {
    return repository.findAll().stream().map(mapper::toDomain).toList();
  }

  @Override
  public List<Project> findAll(ProjectFilterRq rq) {
    return repository.findByFilter(rq.skip(), rq.limit(), rq.name(), rq.start(), rq.end()).stream()
        .map(mapper::toDomain)
        .toList();
  }
}
