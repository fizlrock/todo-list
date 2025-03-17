package dev.fizlrock.todo.repository.mongo;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.ports.IProjectRepository;
import dev.fizlrock.todo.domain.service.dto.ProjectFilterRq;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** ProjectRepositoryMongoImpl */
@Repository
@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "mongo")
public interface ProjectRepositoryMongoImpl
    extends IProjectRepository, MongoRepository<Project, UUID> {

  @Query(
      """
      {$and: [
          { "name": { "$regex": ?0, "$options": "i" } },
          { "startDate": { "$gte": ?1 } },
          { "endDate": { "$lte": ?2 } }
      ]}
      """)
  List<Project> findAll(String name, LocalDate start, LocalDate end, Pageable pageable);

  default List<Project> findAll(ProjectFilterRq rq) {

    int page_number = (int) (rq.skip() / rq.limit()); // Номер страницы
    int page_size = rq.limit().intValue();

    return findAll(
        rq.name().orElse(""),
        rq.start().orElse(LocalDate.of(1971, 1, 1)),
        rq.end().orElse(LocalDate.of(9971, 1, 1)),
        PageRequest.of(page_number, page_size));
  }
}
