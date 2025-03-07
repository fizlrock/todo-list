package dev.fizlrock.todo.repository;

import dev.fizlrock.todo.domain.entity.Project;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** ProjectJPARepository */
@Repository
public interface ProjectJPARepository extends JpaRepository<Project, UUID> {

  Optional<Project> findByName(String name);
}
