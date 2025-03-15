package dev.fizlrock.todo.repository.relational.repository;

import dev.fizlrock.todo.repository.relational.agregate.ProjectAgregate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "relational")
public interface ProjectAgregateRepository extends ListCrudRepository<ProjectAgregate, UUID> {

  Optional<ProjectAgregate> findByName(String name);

  @Query(
      """
          SELECT p.* FROM project p
          WHERE (:name IS NULL OR p.name LIKE '%' || :name || '%')
          AND (:start IS NULL OR p.start_time >= :start)
          AND (:end IS NULL OR p.end_time <= :end)
          LIMIT :limit OFFSET :skip
      """)
  List<ProjectAgregate> findByFilter(
      @Param("skip") Long skip,
      @Param("limit") Long limit,
      @Param("name") String name,
      @Param("start") LocalDate start,
      @Param("end") LocalDate end);
}
