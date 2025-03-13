package dev.fizlrock.todo.repository.jpa;

import dev.fizlrock.todo.domain.entity.Project;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** ProjectJPARepository */
@Repository
@ConditionalOnProperty(name = "todo.repository.impl", havingValue = "jpa")
public interface ProjectJPARepository
    extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {

  Optional<Project> findByName(String name);
}
