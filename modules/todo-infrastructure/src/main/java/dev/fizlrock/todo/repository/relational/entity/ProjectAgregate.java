package dev.fizlrock.todo.repository.relational.entity;

import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/** ProjectAgregate */
@Table("project")
@Getter
@AllArgsConstructor
public class ProjectAgregate implements Persistable<UUID> {

  @Transient boolean isNew;

  @Id UUID id;
  String name;
  String description;
  LocalDate startDate;
  LocalDate endDate;

  @MappedCollection(idColumn = "projectId")
  Set<TaskEntity> tasks;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return isNew;
  }
}
