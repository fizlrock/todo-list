package dev.fizlrock.todo.repository.mongo;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** ProjectRepositoryMongoImpl */
@Repository
@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "mongo")
public interface ProjectRepositoryMongoImpl
    extends IProjectRepository, MongoRepository<Project, UUID> {

  default List<Project> findAll(ProjectFilterRq rq) {
    return null;
  }
}
